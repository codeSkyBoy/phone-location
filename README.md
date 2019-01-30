号码归属地SDK
========================

##支持国内大部分手机号、座机号码的归属地信息查询
采用二分查找算法, 查询速度快;
<br><br>
基于内存字节数组查询, 线程安全;
<br><br>
数据来源于百度、高德, 准确度高.

        平均查询速率: < 1ms/条
        文件大小: 3.4MB
        更新时间: 2019-01-28
        记录条数: 374996

###phone.dat文件格式
        | 4 bytes |                     <-  版本号（如：20190128即2019年01月28日）
        ------------
        | 4 bytes |                     <-  第一个索引的偏移
        ------------
        |  offset |                     <-  记录区
        ------------
        |  index  |                     <-  索引区
        ------------
1. 头部为8个字节，版本号为4个字节，第一个索引的偏移为4个字节;<br>
2. 记录区 中每条记录的格式为"<省份>|<城市>|<省份地域编码>|<省份坐标>|<城市编码>|<城市地域编码>|<城市坐标>|<省份国际ISO编码>\0"。 每条记录以'\0'结束;<br>
3. 索引区 中每条记录的格式为"<号码号段><记录区的偏移><卡类型>"，每个索引的长度为9个字节;<br>

###快速使用
        PhoneLocationSearch search = new PhoneLocationSearch();
        LocationInfo info = search.lookup("010");
        System.out.println(info);
result:

        {
            phoneNumber='010', 
            province='北京市', 
            city='北京城区', 
            operator='null', 
            provinceAdCode='110000', 
            provinceCenter='116.405285,39.904989', 
            cityCode='010', 
            cityAdCode='110100', 
            cityCenter='116.405285,39.904989', 
            isoCode='CN-BJ'
        }