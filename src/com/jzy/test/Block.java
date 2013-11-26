package com.jzy.test;

/**
 * 
 */



/**
 * @author Administrator
 *
 */
public class Block {
	StringBuffer str;//文本块中正文信息
	int linkNum;//文本块有效链接数目（正文子块中链接信息之和）
	int linkLen;//文本块有效链接信息文本长度
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

