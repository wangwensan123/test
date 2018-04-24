package thread.readfile;

public class Main {
/*
 * https://www.cnblogs.com/metoy/p/4470418.html
 * java多线程读取大文件
 */
	public static void main(String[] args) {
		BigFileReader.Builder builder = new BigFileReader.Builder("/home/jack/myfile.txt",new IHandle() {
			
			@Override
			public void handle(String line) {
				System.out.println(line);
    System.out.println("---------------------");
//				increat();
			}
		});
		builder.withThreadSize(Runtime.getRuntime().availableProcessors()).withCharset("utf-8").withBufferSize(1024*1024);
		BigFileReader bigFileReader = builder.build();
		bigFileReader.start();
	}
	
	
}
