package com.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;



public class Test extends TestCase{
	private String sjson = "{\"entries\": {\"lilei\" : {\"age\": 27,\"mobile\" : \"13700000000\",\"address\" : \"Earth somewhere\"}, \"hanmeimei\" : {\"age\": 26,\"mobile\" : \"13700000001\",\"address\" : \"Earth somewhere else\"}}}";
	private JSONObject root;
	private JSONObject curobj;
	private String commandFirst = "";
	private String commandSecond = "";
	private boolean quitFlag = false;
	public void init(){
		try {
			root = new JSONObject(sjson);
			curobj = root;
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	public void getls(){
		Iterator ite = curobj.keys();
		while(ite.hasNext()){
			System.out.println(ite.next().toString());
		}
	}
	public void getcd(JSONObject obj,String path,boolean flag){
		if(!flag){
			flag = obj.has(path);
			if(flag)
				try {
					curobj = (JSONObject) obj.get(path);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			else{
				Iterator ite = obj.keys();
				while(ite.hasNext()){
					String key = ite.next().toString();
					try {
						obj = (JSONObject) obj.get(key);
						getcd(obj,key,flag);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	public void getcat(String key){//�鿴��Ϣ
		Iterator ite = curobj.keys();
		while(ite.hasNext()){
			String temp = ite.next().toString();
			if(temp.equals(key)){
				JSONObject o;
				try {
					o = (JSONObject) curobj.get(key);
					System.out.println(o);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
			}
		}
	}
	public void getadd(){//��������
		System.out.println("key:");
		String key = "";
		key = getfromConsole();
		System.out.println("value:");
		String value = "";
		value = getfromConsole();
		try {
			JSONObject temp = new JSONObject(value);
			curobj.put(key, temp);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		System.out.println("add success!");
	}
	public void getremove(){//ɾ������
		System.out.println("please give the key:");
		String key = "";
		key = getfromConsole();//���չؼ���key
		curobj.remove(key);
		System.out.println(key+" was deleted from JSON");
	}
	public String getfromConsole(){//�ӿ���̨�������� 
		String line = "";
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); 
		try {
			line = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return line;
	}
	private int readString() { //��������
        int com = 0;
        String line = getfromConsole();
		String s[] = line.split(" ");
    	if(s.length>0&&s.length<3){
    		//����һ������
    		String s0 = s[0];
    		if("ls".equals(s0)){
    			com = 1;
    		}else if("cd".endsWith(s0)){
    			com = 2;
    			commandSecond = s[1];//�ڶ�������
    		}else if("cat".equals(s0)){
    			com = 3;
    			commandSecond = s[1];
    		}else if("add".equals(s0)){
    			com = 4;
    		}else if("remove".equals(s0)){
    			com = 5;
    		}else if("!help".equals(s0)){
    			com = 6;
    		}else if("!quit".equals(s0)){
    			com = 7;
    		}else{
    			System.out.println("input error!type \"!help\" for help");
    		}
    	}else{
    		System.out.println("input error!type \"!help\" for help");
    	}
		return com;
    } 
	public void gethelp(){//��������
		StringBuffer sb = new StringBuffer();
		sb.append("ls :command to list the items in current position\n");
		sb.append("cd :command to go to the entry like go to a directory\n");
		sb.append("cat :command to display th item data\n");
		sb.append("add :command to add new address entry to JSON\n");
		sb.append("remove :command to remove one or more address entries\n");
		sb.append("!help :get help\n");
		sb.append("!quit :quit from the application");
		System.out.println(sb.toString());
	}
	public void getquit(Test test){//�˳�����
		test.setQuitFlag(false);
		System.out.println("quited!");
	}
	public static void main(String[] args) throws JSONException {
		String sjson = "{\"entries\": {\"lilei\" : {\"age\": 27,\"mobile\" : \"13700000000\",\"address\" : \"Earth somewhere\"}, \"hanmeimei\" : {\"age\": 26,\"mobile\" : \"13700000001\",\"address\" : \"Earth somewhere else\"}}}";
		Test test = new Test();
		JSONObject root = new JSONObject(sjson);
		JSONObject curobj = root;
		test.setCurobj(curobj);
		test.setQuitFlag(true);
		while(test.isQuitFlag()){
			int n = 1;
			n = test.readString();
			switch(n){
				case 1:test.getls();break;
				case 2:{
					boolean flag = false;//ÿ�β�ѯ�����ñ�־λ
					curobj = root;
					test.getcd(curobj,test.getCommandSecond(),flag);break;
				}
				case 3:test.getcat(test.getCommandSecond());break;
				case 4:test.getadd();break;
				case 5:test.getremove();break;
				case 6:test.gethelp();break;
				case 7:test.getquit(test);break;
				default:break;
			}
		}
	}
	public JSONObject getCurobj() {
		return curobj;
	}
	public void setCurobj(JSONObject curobj) {
		this.curobj = curobj;
	}
	public String getCommandFirst() {
		return commandFirst;
	}
	public void setCommandFirst(String commandFirst) {
		this.commandFirst = commandFirst;
	}
	public String getCommandSecond() {
		return commandSecond;
	}
	public void setCommandSecond(String commandSecond) {
		this.commandSecond = commandSecond;
	}
	public boolean isQuitFlag() {
		return quitFlag;
	}
	public void setQuitFlag(boolean quitFlag) {
		this.quitFlag = quitFlag;
	}
}
