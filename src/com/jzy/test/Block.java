package com.jzy.test;

/**
 * 
 */



/**
 * @author Administrator
 *
 */
public class Block {
	StringBuffer str;//�ı�����������Ϣ
	int linkNum;//�ı�����Ч������Ŀ�������ӿ���������Ϣ֮�ͣ�
	int linkLen;//�ı�����Ч������Ϣ�ı�����
	public Block(){
		linkNum = 0;
		linkLen = 0;
		str = new StringBuffer();
	}
	public void add(Block it){
		linkNum += it.linkNum;
		linkLen += it.linkLen;
		str.append(it.str);
	}
	public void clear(){
		linkNum = 0;
		linkLen = 0;
		str.delete(0,str.length());
	}
}

