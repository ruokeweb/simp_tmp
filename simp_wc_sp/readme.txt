У��С�����ڴ��ļ����½��а汾����

======================================================================����һ���ָ���=========================================================================================
#ajax �Զ������󷽷�
��װ��������Ϊ�˷����������ˡ�
1����ҳ������ import request from '@/common/request.js';
2��ҳ����ֱ��ʹ��
request.ajax({
    url:'',
    data:'',
    header: "",  
    method:'', 
    success:(res) =>{
        
    },
    
    error:(res) =>{
        
    }
})

3��ע�⣺
����url ֻ��Ҫ���� app.serverPath ֮��� ·��
��headerΪ Content-Type": "application/json" ʱ������Ҫ���룬��Ϊ'content-type': 'application/x-www-form-urlencoded',����Ҫ����
header : 'application/x-www-form-urlencoded'
method ��Ϊpost ����ʱ,����Ҫ����
����������ϴ�ʹ�� uni.request ԭ����������

======================================================================����һ���ָ���=========================================================================================
# vueX store ״̬����
	state: {
		hasLogin: false,
		token: {}
		
	}
    state ��Ϊ����ȫ�ֱ��� �� hasLogin �Ƿ��¼
    ���ڵ�¼ҳ���¼�ɹ���
    ͨ��mapMutations�޸�״̬
    import {
        mapMutations
    } from 'vuex';
    ��method ��д�� 
    //���µ�¼״̬
    ...mapMutations(['login']),
    //��ִ�и���״̬�ķ���
    //�޸�vueX state
    this.login(res.data.data.token);    
    
    //ͨ�� mapState ��ȡvuex store �еı���
    import {
        mapState
    } from 'vuex';
    
    Ȼ��ͨ�����㺯��
    computed:{
    	...mapState(['hasLogin'])
    },
    this.hasLogin ����ֱ��ȡ���Ƿ��¼��ֵ ��  ��ϸ��˵��ο�  vueX �ٷ���վ
    
-----------------------------------------------------------------------------------------------------
2019-07-13 v2.0 ���
2.0�汾
1���Զ���¼
2������ʶ��
3��С����֪ͨ��Ϣ
4�����