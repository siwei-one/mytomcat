可以使用SAX来查询或者阅读XML文档。SAX可以快速扫描一个大型的XML文档，当它找到查询标准时就会立即停止，然后再处理之。
DOM是把XML全部加载到内存中建立一棵树之后再进行处理。所以DOM不适合处理大型的XML【会产生内存的急剧膨胀】。

同理，DOM的弱项就是SAX的强项，SAX不必把全部的xml都加载到内存中。
但是SAX的缺点也很明显，它只能对文件顺序解析一遍，不支持对文件的随意存取。SAX也仅仅能够读取文件的内容，并不能修改内容。
DOM可以随意修改文件树，从而修改了xml文件。

SAX适于处理下面的问题：

	1、对大型文件进行处理；
	
	2、只需要文件夹的部分内容，或者只需从文件中得到特定信息。
	
	3、想建立自己的对象模型的时候。

DOM适于处理下面的问题：

	1、需要对文件进行修改；
	
	2、需要随机对文件进行存取