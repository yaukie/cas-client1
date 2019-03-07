package org;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import org.yaukie.helper.PublishWebServiceHelper;
import org.yaukie.soap.HelloService2;
public class Test {
	
	static void test1(){
		
	}
	
	/**
	 * 猴子吃葡萄的问题,,
	 * 输入葡萄个数n,输出能吃掉的所有方式
	 * @param amounts
	 */
	static void test2(int amounts ){
			//构造一个一维数组,存储可以分解成的数字集合
			int[] array = new int[]{1,2};
			
			List<int[]> list = new LinkedList<int[]>();
			
			//将amounts进行分解
			for(int n=0;n<=amounts;n++){
				for(int m=0;m<=amounts/2;m++){
					if(amounts == n*array[0]+m*array[1]){
							int[] temArr = new int[]{n,m};
							list.add(temArr);
					}
				}
				
			}
			//将葡萄数量进行分解,
			List<int[]> dataList = new LinkedList<int[]>();
			for(int[] a : list ){
				//1 的个数
				int one = a[0];
				//2的个数
				int two = a[1];
				int[] newArr = new int[5];
				
				if(one == 0 ){
					for(int i=0;i<two;i++){
						newArr[i]=2;
					}
				}else if(two ==0 ){
					for(int i=0;i<one;i++){
						newArr[i]=1;
					}
				}else{
					for(int i=0;i<one;i++){
						newArr[i]=1;
					}
					for(int i=one;i<two+one;i++){
						newArr[i]=2;
					}
				}
				
				dataList.add(newArr);
			}
		
			Set<int[]> resultSet = new LinkedHashSet<int[]>();
			int tem;
			int[] arr;
			for(int[] a : dataList ){
						for(int i =0;i<a.length;i++){
							 	  tem = a[i];
							 	  for(int j=i+1;j<a.length;j++){
							 		  int bt = a[j];
							 		  		a[j]=a[j+1];
							 		  		a[j+1]=bt;
							 		  		resultSet.add(a);
							 	  }
						}
				
			}
			
			Iterator<int[]> iter = resultSet.iterator();
			int count=0;
			while(iter.hasNext()){
				int[] temp = iter.next();
				System.out.println("第"+(++count)+"组");
				for(int i=0;i<temp.length;i++){
					System.out.print(temp[i]+" ");
				}
				System.out.println();
				
			}
			
	}
	
	static void test3(){
		
	}
	
	/**
	 * 拼火柴的问题,,
	 * 输入火柴个数,输出a+b=c,b+a=c的等式,a,b,c都是火柴棒所能拼接成的数字
	 */
	 static void test (){

			System.out.println("请输入火柴棍总数:\n");
			Scanner sc = new Scanner(System.in);
			//自定义输入的火柴棍的总个数;
			//也就是说,拼接整数所用的总的火柴棍个数不能超过这个值
			int maxSize = sc.nextInt();
			System.out.println("您输入的火柴棍总数为:"+maxSize);
			//定义存放拼接0-9这是个数字所需要的火柴棍个数
			int[] array = new int[]{6,2,5,5,4,5,6,3,7,6};
			//用于临时存放现有火柴棍拼接的等式形式
			Set<String> set = new HashSet();
			int add ;//累加值
			int ten;//十位数
			int ge;//个位数
			int eqVal;//等号右边总的火柴棍
			
			for(int i =0 ;i<array.length;i++)
			 {
					int temVal;//等号左边总的火柴棍
					int a = array[i];
				for(int j =0;j<array.length;j++){
					int b=array[j];
					//等号左边总火柴棍
				    temVal = a+b+4;
					//累加后的值
					 add = i+j;
						if(i==j){
							//如果是两位数
							if(add>9){
								ten=add/10;
								ge=add-ten*10;
								//等号右边数字所需要的火柴棍总数
								eqVal=array[ten]+array[ge];
							}else{
								//等号右边的数字所需火柴棍总数
								eqVal=array[add];
							}
							temVal=temVal+eqVal;
							if(temVal<=maxSize){
								set.add(i+""+"+"+j+""+"="+add+"\n");
							}
								
						}else{
							//如果是两位数
							if(add>9){
								ten=add/10;
								ge=add-ten*10;
								//等号右边数字所需要的火柴棍总数
								eqVal=array[ten]+array[ge];
							}else{
								//等号右边的数字所需火柴棍总数
								eqVal=array[add];
							}
							temVal=temVal+eqVal;
							if(temVal<=maxSize){
								set.add(i+""+"+"+j+""+"="+add+"\n");
								set.add(j+""+"+"+i+""+"="+add+"\n");
							}
							
						}
		
				}
			 }
			System.out.println("最终输出的等式为:\n");   
		   for(String str : set ){
			   System.out.println(str);   
		   }
			
		
	 }
	public static void main(String[] args) {
		test2(5);
	}
}
