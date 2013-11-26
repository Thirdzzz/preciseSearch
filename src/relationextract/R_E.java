package relationextract;

import java.io.*;
import java.text.DecimalFormat;
import java.util.*;
import java.util.regex.*;
import ICTCLAS.I3S.AC.ICTCLAS50;

public class R_E {
	String [] words=null;
	public ArrayList<String[]> concepts=new ArrayList<String[]>();//�����洢�����
	public ArrayList<String> sentences=new ArrayList<String>();		//�����洢����
	public HashMap<String,Double> hmwords=new HashMap<String,Double>();//�����洢���ʳ��ֵľ�����	����������
	public HashMap<String[],Double> hmconcepts=new HashMap<String[],Double>();//�����洢����Գ��ֵľ�����	����������
	public HashMap<String[],Double> hmsupport=new HashMap<String[],Double>();//�����洢����Ե�֧�ֶ�
	public HashMap<String[],Double> hmconf=new HashMap<String[],Double>();//�����洢����Ե����Ŷ�
	public HashMap<String[],HashMap<String,Double>> hmrelation=new HashMap<String[],HashMap<String,Double>>();//�����洢��������еĹ�ϵ����ϵƵ�ʣ�~~~����<����ԣ�<���ʣ�Ƶ��>��������>
	public ArrayList<String> relations=new ArrayList<String>();//�����洢����ԵĹ�ϵ 		~~~��ϵӦ�þ��Ƕ��ʰ�
	public HashMap<String[],ArrayList<String>> hmrulesentences=new HashMap<String[],ArrayList<String>>();//�����洢����ԵĹ�ϵ����伯		~~~��¼����<����ԣ�����Գ��ֵľ��Ӽ��ϡ���>
	//public ArrayList<Pattern> rules=new ArrayList<Pattern>();
	//Pattern[] rules={};
	public String[] rules={"c0(.)*(((��Ҫ)?(��Ϊ))|((��Ҫ)?(����))|(��Ҫ��)|(�ֱ���))(.)*c1","ĳЩc0��(.)*c1","c1��c0��һ��","c1��һ��c0","c0���ر���(.)*c1","��(.)*c0(.)*����Ϊ(.)*c1"};
	public String[] rulename={"����1","����2","����3","����4","����5","����6"};//ƥ��ģʽ����
	public ArrayList<String> results=new ArrayList<String>();//ƥ��ɹ��Ľ��
	
	/**
	 * ��ȡ�����
	 */
	public void setConcepts(){		
		try {
			String path="D:\\Documents and Settings\\Administrator\\Workspaces\\MyEclipse 8.6\\RE\\���.txt";
			FileReader freader;																		//~~~���ˣ����RE�ļ��л�û�д����أ�����
			freader = new FileReader(path);
			BufferedReader breader=new BufferedReader(freader);
			String line=null;
				while((line=breader.readLine())!=null){
					words=line.split(",");				//~~~�������ȡ
					for(int i=0;i<words.length;i++){
						hmwords.put(words[i].replace("/", "").trim(), 0.0);		//~~~replace("/", "")�������е�/ȥ��
						System.out.println(hmwords);							//~~~hmwords�����������	ȥ��/�����Ҷ˿ո�ĵ���			
						for(int j=i+1;j<words.length;j++){
							String[] temp=new String[2];
							temp[0]=words[i].replace("/", "").replace(" ", "").trim();		//~~~trim()��ȥ���������˵Ŀո�
							temp[1]=words[j].replace("/", "").replace(" ", "").trim();		//~~~������ȥ������������/�Ϳո��
							if(temp[0].length()>0&&temp[1].length()>0){
								concepts.add(temp);								//����������ʱ�����뵽������С� ��  ���ǲ�ֹһ����������������ɵĶ�
								
							}
						}
					}					
				}
				for(int i=0;i<concepts.size();i++){
					//System.out.println(concepts.get(i)[0]+concepts.get(i)[1]);
					hmconcepts.put(concepts.get(i), 0.0);		//~~~��Щ����hashmap<string,double>��put�Ǹ���Ӧkeyֵ����valueֵ
					hmsupport.put(concepts.get(i), 0.0);		//~~~����Щ����Ը���ֵ
					hmconf.put(concepts.get(i), 0.0);
					HashMap<String,Double> hm=new HashMap<String,Double>();
					hmrelation.put(concepts.get(i), hm);		//~~~�����ϵ�����ӣ���¼���Ǹ���Ժ�����֮���<���ʺͶ��ʳ��ֵ�Ƶ��>
				}
				
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * ѭ����ȡ���ϼ��е����£�����������Ļ�ȡ���Ӽ��÷���
	 */
	public void setArticles(String pathCorpus)throws FileNotFoundException, IOException {
															//~~~setArticles�Ƕ�һ��·���µ��ļ������ļ����е��ı����д����ݹ鴦�������е�|����|���ֳ���
		try {
            File file = new File(pathCorpus);
            if (!file.isDirectory()) {		//��һ���ļ�                    
            	setSenteces(file);

            } else if (file.isDirectory()) {		//��һ���ļ���
                    String[] filelist = file.list();
                    for (int i = 0; i < filelist.length; i++) {
                            File readfile = new File(pathCorpus + "\\" + filelist[i]);
                            if (!readfile.isDirectory()) {
                            	setSenteces(readfile);
                            } else if (readfile.isDirectory()) {
                            	setArticles(pathCorpus + "\\" + filelist[i]);			//~~~��Ӵ���ݹ�Ӵ
                            }
                    }

            }

    } catch (FileNotFoundException e) {
            System.out.println("readfile()   Exception:" + e.getMessage());
    }

	}
	
	/**
	 * ��ȡһƪ���µľ��Ӽ�
	 * @throws IOException 
	 */
	public void setSenteces(File file) throws IOException{			//~~~����˵setSenteces�Ǵ������ı����ݱ����Ż���|����|
		FileReader freader=new FileReader(file);
		BufferedReader breader=new BufferedReader(freader);
		String text=null;
		while((text=breader.readLine())!=null){
			int start=0,end=0,count=0;
			for(int i=0;i<text.length();i++)
            {
				count++;
         	   if(text.charAt(i)=='��'||text.charAt(i)=='��'||text.charAt(i)=='��'||text.charAt(i)=='��'||text.charAt(i)==';'||text.charAt(i)==':'||text.charAt(i)=='\n'||(i==text.length()-1))
         	   {
         		   end=count;
         		   sentences.add(text.substring(start, end));		//~~~�־䣬����������Ϊ�ָ������ֿ�|����|�����¼��sentences��
         		   start=count;
         	   }
            }		
			
		}
	}
	
	/**
	 * �����ֽ����¼�¼���ӣ�����������֮����ֵĶ��ʣ�������֧�ֶȺ����Ŷ�
	 */
	public void setSC(){
		for(int i=0;i<sentences.size();i++){			//~~~sentences�м�¼���������ļ��ﱻ���ŷָ�ľ���
			Iterator iterword=hmwords.keySet().iterator();
			while(iterword.hasNext()){//���¸ôʳ��ֵľ�����
				String word=(String)iterword.next();
				if(word.length()>0&&sentences.get(i).contains(word)){
					Double num=hmwords.get(word)+1;		//~~~����word��һ�������г���һ�Σ���hmwords��valueֵ�ͼ�һ����¼�ĵ���word�ĳ���Ƶ��
					hmwords.put(word, num);
				}
			}
			Iterator iterconcepts=hmconcepts.keySet().iterator();
			while(iterconcepts.hasNext()){				//���¸���Գ��ֵľ�����
				String[] concept=(String[])iterconcepts.next();
				if(sentences.get(i).contains(concept[0])&&sentences.get(i).contains(concept[1])){		//~~~��ĳһ�����Ӱ����иø����ʱ
					if(hmrulesentences.containsKey(concept)){//��ȡ����Ե����ڹ�ϵ�����ȡ�ľ��Ӽ�
						hmrulesentences.get(concept).add(sentences.get(i));
					}else{
						
						ArrayList<String> sentence=new ArrayList<String>();
						sentence.add(sentences.get(i));
						hmrulesentences.put(concept, sentence);		//~~~hmrulesentences��¼���Ǹ���� �� ����Գ��ֵľ��Ӽ���
					}
					Double num=hmconcepts.get(concept)+1;		//~~~hmconceptsҲ��¼����Գ��ֵ�Ƶ��
					hmconcepts.put(concept, num);
					//������¸���Լ���ֵĶ��ʣ�������|���־�| �� |���Ӿ�| 	�ĸ���Ժ�����ֵĶ���
					//System.out.println(concept[0]);			
					//System.out.println(concept[1]);
					String temprelation=null;				//~~~һ��Ҫ��ʼ���¶��ʹ�ϵ��
					String ba="(("+concept[0]+")|("+concept[1]+"))(.)*��(.)*(("+concept[0]+")|("+concept[1]+"))(.)*";	//~~~���־�
					String bei="(("+concept[0]+")|("+concept[1]+"))(.)*��(.)*(("+concept[0]+")|("+concept[1]+"))(.)*";	//~~~���־�
					//lixue
					Pattern p1=Pattern.compile(ba);
					Matcher m1=p1.matcher(sentences.get(i));			//~~~JAVA������ʽ������
					Pattern p2=Pattern.compile(bei);
					Matcher m2=p2.matcher(sentences.get(i));
					if(m1.find()||m2.find()){
						int c1=sentences.get(i).lastIndexOf(concept[0]);	//~~~���ظ��������ھ����г��ֵ�λ��
						int c2=sentences.get(i).lastIndexOf(concept[1]);
						
						temprelation=sentences.get(i).substring(Math.max(c1, c2));	//~~~temprelation��¼���ǲ���������ʵľ��ӵ���󲿷�
					}else{
						int begin=sentences.get(i).indexOf(concept[0])+concept[0].length();
						int end=sentences.get(i).indexOf(concept[1]);
						if(begin<end){
							temprelation=sentences.get(i).substring(begin, end);	//~~~��صû᲻���е�ͻأ��������
						}
					}
					//��temprelation���зִΣ�����ICTCLAS��ȡ������Ϊ���ʵ���Ϊ��ϵ����
					ArrayList<String> arrayrelation=fenci(temprelation);		//~~~����ICTCLAS�ִʣ���ö�����Ϊ��ϵ
					if(arrayrelation!=null){
						for(int j=0;j<arrayrelation.size();j++){
							String relation=arrayrelation.get(j);
							if(hmrelation.get(concept).containsKey(relation)){	//~~~������ԵĹ�ϵ���ֺʹ�������¼����
								Double numr=hmrelation.get(concept).get(relation)+1;
								hmrelation.get(concept).put(relation, numr);
							}else{
								hmrelation.get(concept).put(relation, 1.0);		//~~~��ʼ��
							}
						}
					}
					
				}
			}
		}
		Iterator itersupport=hmsupport.keySet().iterator();
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(4);					//~~~�²���ĳ�ֹ淶����ʾС������
		df.setMinimumFractionDigits(4);
		while(itersupport.hasNext()){					//�������Ե�֧�ֶ�
			String[] concept=(String[])itersupport.next();
			Double value=hmconcepts.get(concept)/sentences.size();	//֧�ֶ�
			hmsupport.put(concept, value);
			System.out.println("֧�ֶȣ���"+concept[0]+"��"+concept[1]+"��"+df.format(value)+"��");
		}
		Iterator iterconf=hmconf.keySet().iterator();
		while(iterconf.hasNext()){					//�������Ե����Ŷ�
			String[] concept=(String[])iterconf.next();
			Double value=hmconcepts.get(concept)/hmwords.get(concept[0]);
			hmconf.put(concept, value);
			System.out.println("���Ŷȣ���"+concept[0]+"��"+concept[1]+"��"+df.format(value)+"��");	//~~~�������Ե�֧�ֶȺ����Ŷ�
		}
	}
	
	
	
	public ArrayList<String> fenci(String temprelation){		//~~~�ִʺ���ʹ����ICTCLAS50������⣬�д�ѧϰ�˽⣿����
		String nativeStr=null;
		try
		{
    		ICTCLAS50 testICTCLAS50 = new ICTCLAS50();
			//�ִ�������·��
			String argu = ".";
			//��ʼ��
		if (testICTCLAS50.ICTCLAS_Init(argu.getBytes("GB2312")) == false)
			{
				//System.out.println("Init Fail!");
				return null;
			}

		String sInput=temprelation;
		byte nativeBytes[] = testICTCLAS50.ICTCLAS_ParagraphProcess(sInput.getBytes("GB2312"), 0, 1);
			//System.out.println(nativeBytes.length);
			nativeStr = new String(nativeBytes, 0, nativeBytes.length, "GB2312");
			//System.out.println("The result is ��" + nativeStr);
			ArrayList<String> array=new ArrayList();
			String [] arraystr=nativeStr.split(" ");
			for(int i=0;i<arraystr.length;i++){
				if(arraystr[i].contains("/v")){
					array.add(arraystr[i].substring(0, arraystr[i].indexOf("/v")));
				}
			}
			testICTCLAS50.ICTCLAS_Exit();
			return array;
		}
		catch (Exception ex)
		{
		}
		return null;
	}
	
	/**
	 * ��ȡ����Թ�ϵ
	 */
	public void setRelations(){
		for(int i=0;i<concepts.size();i++){
			String[] concept=concepts.get(i);
			if(hmsupport.get(concept)>0.003&&hmconf.get(concept)>0.01){			//~~~������Ե�֧�ֶȺ����Ŷ�����һ��������
				Iterator iterrelation=hmrelation.get(concept).keySet().iterator();
				String temprelation=null;
				Double tempf=0.0;		//~~~��¼���Ǹ����ʹ������|��ϵ|
				while(iterrelation.hasNext()){
					String relation=(String)iterrelation.next();		//~~~����Թ�ϵrelation
					Double f=hmrelation.get(concept).get(relation);		//~~~����Թ�ϵrelation���ֵĴ���
					if(f>tempf){
						tempf=f;
						temprelation=relation;
					}

				}
				if(tempf>=5){
					String relation="����"+concept[0]+"��"+concept[1]+"����"+temprelation+"��";
					relations.add(relation);			//~~~�������ʹ�ö��ʲ�������Σ����Ǿ���Ϊ�����й�ϵ�� ��¼��relations[]�ַ�������
				}
			}
		}
	}
	
	/**
	 * ��ȡ�����ƥ��ɹ��Ĺ���
	 */
	public void setrules(){
		Iterator iterrules=hmrulesentences.keySet().iterator();
		while(iterrules.hasNext()){
			String[] content=(String[])iterrules.next();			//~~~content�����
			ArrayList<String> sent=hmrulesentences.get(content); 	//~~~sent����Գ��ֵľ��Ӽ�
			for(int i=0;i<sent.size();i++){				
				String ora=sent.get(i);								//~~~ora��������
				for(int j=0;j<rules.length;j++){	//ѭ����ȡģʽƥ��				//~~~rules�м�¼���Ǹ����֮��Ĺ��򣬹�ϵ��eg��c0����c1����c0.������c1ʲô�ġ�����
					String regex=rules[j].replace("c0", content[0]).replace("c1", content[1]) ;		//~~~�޸Ĺ����������ʽ				
					Pattern p=Pattern.compile(regex);
					Matcher m=p.matcher(ora);
					while(m.find()){
						String result="����"+content[0]+"��"+content[1]+"����"+rulename[j]+"��";
						if(!results.contains(result)){
							results.add(result);		//~~~��c0��c1֮����ڵĹ�ϵ�ʹ��������¼��result��
						}
					}
				}
			}
		}
	}
	
	/**
	 * @param args
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException, IOException {
		String pathCorpus="D:\\Documents and Settings\\Administrator\\Workspaces\\MyEclipse 8.6\\RE\\ʾ��";
		// TODO Auto-generated method stub
		R_E re=new R_E();
		re.setArticles(pathCorpus);
		re.setConcepts();
		re.setSC();
		re.setRelations();
		re.setrules();
		System.out.println(re.relations);//����磨��Ԥ����Ӧ�������룩��������Ϊ"Ԥ����Ӧ��"����ȡ�Ĺ�ϵΪ"��"
		System.out.println(re.results);
		
		//���ÿһ������Լ���ֵ����дʵ�Ƶ��public HashMap<String[],HashMap<String,Double>> hmrelation
		Iterator iterrelations=re.hmrelation.keySet().iterator();
		while(iterrelations.hasNext()){
			String[] concept=(String[])iterrelations.next();
			
			HashMap<String,Double> hmrela=re.hmrelation.get(concept);
				if(hmrela.size()>0&&hmrela!=null){
				System.out.println("����ԣ�"+concept[0]+" "+concept[1]);
				Iterator iterrela=hmrela.keySet().iterator();
				while(iterrela.hasNext()){
					String rela=(String)iterrela.next();
					Double frela=hmrela.get(rela);
					System.out.println("���ʣ�"+rela+" Ƶ�ʣ�"+frela);
				}
			}
	}

	}
}
