package org;

import java.io.FileInputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Main {
		
	static class 	MyClassLoader extends ClassLoader{
		
		private String classPath; 
		
		public MyClassLoader(String classPath ) {
			this.classPath=classPath ;
		}
		
		private byte[] loadByte(String name ) throws Exception {
			name = name.replaceAll("\\.", "/");
			
			FileInputStream fis = new FileInputStream(classPath+"/"+name+".class");
			
			int length = fis.available();
			
			byte[] b = new byte[length];
			
			fis.read(b);
			fis.close();
			
			return b;
			
		}
		
		@Override
		protected Class<?> findClass(String name) throws ClassNotFoundException {
						Class<?> cls=null;
						try {
							byte[] data = loadByte(name);
							 cls= super.defineClass(data, 0, data.length);
							 return cls;
						} catch (Exception e) {
							e.printStackTrace();
							throw new ClassNotFoundException();
						}
		}
		
		
	}
	

		public static void main(String[] args) {
			MyClassLoader myClassLoader = new MyClassLoader("E:\\maven\\small\\cas-client1\\target\\classes\\org\\");
			try {
				Class<?> cls = myClassLoader.findClass("test");
				Object obj = cls.newInstance();
				Method m = cls.getDeclaredMethod("hello", null);
				m.invoke(obj, null);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
	
}
