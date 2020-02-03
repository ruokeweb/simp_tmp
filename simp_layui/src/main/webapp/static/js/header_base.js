function getRoot(){
    var basePath=document.domain;
    var http="http://";
    var port=":81";
    var path=http+basePath+port;
    return path;
}