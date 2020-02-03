package com.mpri.aio.system.LicenseAuth;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 与系统相关的一些常用工具方法.
 */
public class MacCpuSeriaUtils{
    private static String macAddressStr = null;
    private static String computerName = System.getenv().get("COMPUTERNAME");
    private static String DEF_IP = "0.0.0.0";
    private static final String[] windowsCommand = { "ipconfig", "/all" };
    private static final String[] linuxCommand = { "/sbin/ifconfig", "-a" };
    private static final Pattern macPattern = Pattern.compile(".*((:?[0-9a-f]{2}[-:]){5}[0-9a-f]{2}).*",
            Pattern.CASE_INSENSITIVE);

    /**
     * 限制创建实例
     */
    private MacCpuSeriaUtils() {

    }

    /**
     * 获取unix网卡的mac地址. 非windows的系统默认调用本方法获取. 如果有特殊系统请继续扩充新的取mac地址方法.
     */
    public static String getMAC_linux() {
        String mac = null;
        BufferedReader bufferedReader = null;
        Process process = null;
        try {
            // linux下的命令，一般取eth0作为本地主网卡
            process = Runtime.getRuntime().exec("ifconfig eth0");
            // 显示信息中包含有mac地址信息
            bufferedReader = new BufferedReader(new InputStreamReader(
                    process.getInputStream()));
            String line = null;
            int index = -1;
            while ((line = bufferedReader.readLine()) != null) {
                // 寻找标示字符串[hwaddr]
                index = line.toLowerCase().indexOf("hwaddr");
                if (index >= 0) {// 找到了
                    // 取出mac地址并去除2边空格
                    mac = line.substring(index + "hwaddr".length() + 1).trim();
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("获取mac信息错误");
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e1) {
                System.out.println("获取mac信息错误");
            }
            bufferedReader = null;
            process = null;
        }
        return mac;
    }


    public static String getMAC(){
        String os = getOSName();
        String mac = "";
        if (os.startsWith("windows")) {
            mac = getMAC_windows();
        } else if (os.startsWith("linux")) {
            mac = getMAC_linux();
        }
        if(!StringUtils.isNotBlank(mac)){
            mac="null";
        }
        return mac;
    }


    /**
     * 获取CPU序列号
     *
     * @return
     */
    public static String getCPUID_Windows() {
        String result = "";
        try {
            File file = File.createTempFile("tmp", ".vbs");
            file.deleteOnExit();
            FileWriter fw = new java.io.FileWriter(file);
            String vbs = "Set objWMIService = GetObject(\"winmgmts:\\\\.\\root\\cimv2\")\n"
                    + "Set colItems = objWMIService.ExecQuery _ \n"
                    + "   (\"Select * from Win32_Processor\") \n"
                    + "For Each objItem in colItems \n"
                    + "    Wscript.Echo objItem.ProcessorId \n"
                    + "    exit for  ' do the first cpu only! \n" + "Next \n";

            fw.write(vbs);
            fw.close();
            Process p = Runtime.getRuntime().exec(
                    "cscript //NoLogo " + file.getPath());
            BufferedReader input = new BufferedReader(new InputStreamReader(
                    p.getInputStream()));
            String line;
            while ((line = input.readLine()) != null) {
                result += line;
            }
            input.close();
            file.delete();
        } catch (Exception e) {
            System.out.println("获取cpu信息错误");
        }
        return result.trim();
    }

    public static String getCPUID_linux()  {
        String result = "";
        String CPU_ID_CMD = "dmidecode";
        BufferedReader bufferedReader = null;
        Process p = null;
        try {
            p = Runtime.getRuntime().exec(new String[]{ "sh", "-c", CPU_ID_CMD });// 管道
            bufferedReader = new BufferedReader(new InputStreamReader(
                    p.getInputStream()));
            String line = null;
            int index = -1;
            while ((line = bufferedReader.readLine()) != null) {
                // 寻找标示字符串[hwaddr]
                index = line.toLowerCase().indexOf("uuid");
                if (index >= 0) {// 找到了
                    // 取出mac地址并去除2边空格
                    result = line.substring(index + "uuid".length() + 1).trim();
                    break;
                }
            }

        } catch (IOException e) {
            System.out.println("获取cpu信息错误");
        }
        return result.trim();
    }

    public static String getCPUId() {
        String os = getOSName();
        String cpuId = "";
        if (os.startsWith("windows")) {
            cpuId = MacCpuSeriaUtils.getCPUID_Windows();
        } else if (os.startsWith("linux")) {
            cpuId = MacCpuSeriaUtils.getCPUID_linux();
        }
        if(!StringUtils.isNotBlank(cpuId)){
            cpuId="null";
        }
        return cpuId;
    }

    /**
     * 获取当前操作系统名称. return 操作系统名称 例如:windows xp,linux 等.
     */
    public static String getOSName() {
        return System.getProperty("os.name").toLowerCase();
    }

    /**
     * 获取多个网卡地址
     *
     * @return
     * @throws IOException
     */
    private final static List<String> getMacAddressList() throws IOException {
        final ArrayList<String> macAddressList = new ArrayList<String>();
        final String os = System.getProperty("os.name");
        final String command[];

        if (os.startsWith("Windows")) {
            command = windowsCommand;
        } else if (os.startsWith("Linux")) {
            command = linuxCommand;
        } else {
            throw new IOException("Unknow operating system:" + os);
        }
        // 执行命令
        final Process process = Runtime.getRuntime().exec(command);

        BufferedReader bufReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        for (String line = null; (line = bufReader.readLine()) != null;) {
            Matcher matcher = macPattern.matcher(line);
            if (matcher.matches()) {
                macAddressList.add(matcher.group(1));
                // macAddressList.add(matcher.group(1).replaceAll("[-:]",
                // ""));//去掉MAC中的“-”
            }
        }

        process.destroy();
        bufReader.close();
        return macAddressList;
    }

    /**
     * 获取一个网卡地址（多个网卡时从中获取一个）
     *
     * @return
     */
    public static String getMAC_windows() {
        if (macAddressStr == null || macAddressStr.equals("")) {
            StringBuffer sb = new StringBuffer(); // 存放多个网卡地址用，目前只取一个非0000000000E0隧道的值
            try {
                List<String> macList = getMacAddressList();
                for (Iterator<String> iter = macList.iterator(); iter.hasNext();) {
                    String amac = iter.next();
                    if (!amac.equals("0000000000E0")) {
                        sb.append(amac);
                        break;
                    }
                }
            } catch (IOException e) {
//                e.printStackTrace();
            }

            macAddressStr = sb.toString();

        }

        return macAddressStr;
    }


    /**
     * 获取客户端IP地址
     *
     * @return
     */
    public static String getIpAddrAndName() throws IOException {
        return InetAddress.getLocalHost().toString();
    }

    /**
     * 获取客户端IP地址
     *
     * @return
     */
    public static String getIpAddr() throws IOException {
        return InetAddress.getLocalHost().getHostAddress().toString();
    }

    /**
     * 获取电脑唯一标识
     *
     * @return
     */
    public static String getComputerID() {
        String id = getMAC_windows();
        if (id == null || id.equals("")) {
            try {
                id = getIpAddrAndName();
            } catch (IOException e) {
//                e.printStackTrace();
            }
        }
        return computerName;
    }


    /**
     * 测试用的main方法.
     * @throws Exception
     */
    public static void main(String[] argc) throws Exception {
//        String mac = getCPUId();
//        System.out.println(mac);
//        String mac1 = getMAC();
//        System.out.println("mac地址为："+mac1);
//        String mac11 = getComputerID();
//        System.out.println("getComputerID地址为："+mac11);
//        String mac111 = getIpAddr();
//        System.out.println("getIpAddr地址为："+mac111);
        Set<String> localMacAddress = getLocalMacAddress();
        System.out.println("");
    }


    /*
     * 通过路由表找到连接默认网关的ip接口，通过这些ip接口（InetAddress），对应上他们所在的网络接口（NetworkInterface），然后获取网络接口的mac地址
     * @return Set<String>：（a0-xx-xx-xx-xx-cb，0c-xx-xx-xx-xx-39)
     */
    public static Set<String> getLocalMacAddress() throws UnknownHostException, SocketException {
        Set<String> macSet = new HashSet<String>();

        for(Object ipAndGatewayObj : getIpAndGateway()){
            //String gateway = ((JSONObject)ipAndGatewayObj).get("gateway").toString();
            String ip = ((JSONObject)ipAndGatewayObj).get("ip").toString();
            InetAddress ia = InetAddress.getByName(ip);
            NetworkInterface ni = NetworkInterface.getByInetAddress(ia);
            byte[] mac = ni.getHardwareAddress();
            //byte->int->16进制->string
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < mac.length; i++) {
                if (i != 0)
                    sb.append("-");
                String tmp = Integer.toHexString(mac[i] & 0xFF);// 将byte转为正整数。然后转为16进制数
                sb.append(tmp.length() == 1 ? 0 + tmp : tmp);
            }
            System.out.println("网络接口名称ni："+ni.getName()+"---"+"mac地址："+sb.toString().toLowerCase()+"---"+"ip地址："+ip);
            macSet.add(sb.toString().toLowerCase());
        }
        return macSet;
    }

    public static List getIpAndGateway(){
        String os = System.getProperty("os.name");
        List<JSONObject> netInfoList = new ArrayList<JSONObject>();
        if (os != null && os.startsWith("Windows")) {
            try{
                String command = "route print";
                Process p = Runtime.getRuntime().exec(command);
                BufferedReader br = new BufferedReader(new InputStreamReader(
                        p.getInputStream()));
                String line;

                String[] tmp = null;
                JSONObject netInfo = null;
                while ((line = br.readLine()) != null) {
                    tmp = line.trim().split("\\s+");
                    if (tmp.length > 0 && tmp[0].equals(DEF_IP)) {
                        netInfo = new JSONObject();
                        netInfo.put("gateway", tmp[2]);
                        netInfo.put("ip", tmp[3]);
                        netInfoList.add(netInfo);
                    }
                }
                br.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        return netInfoList;
    }

}