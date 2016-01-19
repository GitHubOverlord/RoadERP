package com.lida.road.entity;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;


/**
 * 字符串工具类
 */
public class StrTool {

    /**
     * 对字符串转码，用于解决中文乱码问题。
     * @param original 原始字符串。
     * @param charset 字符集编码："UTF-8","ISO-8859-1","GBK","GB2312"等.
     * @return 转码后的字符串.
     */
    public static String transCharset(String original, String charset) {
        if(original == null)
            return null;
        StringBuffer sb = new StringBuffer(original.length() + 16);
        for(int i = 0; i < original.length(); i++){
            char c = original.charAt(i);
            if(c >= 0 && c <= 255){
                sb.append(c);
            }else{
                byte[] b;
                try{
                    b = String.valueOf(c).getBytes(charset);
                }catch(Exception ex){
                    ex.printStackTrace();
                    b = new byte[0];
                }
                for(int j = 0; j < b.length; j++){
                    int k = b[j];
                    if(k < 0)
                        k += 256;
                    sb.append("%" + Integer.toHexString(k).toUpperCase());
                }
            }
        }
        return sb.toString();
    }
    
    /**
     * 把中文编码字符串转码为ISO-8859-1编码字符串。
     * 对于附件下载文件名出现乱码的情况下有用
     * @param chsStr
     * @return
     */
    public static String transChs2Iso(String chsStr) {
    	if(chsStr == null) {
    		return null;
    	}
		try {
			return new String(chsStr.getBytes("GBK"), "ISO-8859-1");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return chsStr;
		}
	}

    /**
     * 把字符串pOriginalString转化为中文字符串（用来解决乱码问题）
     * @param pOriginalString 需要解码的字符串 说明：如果pOriginalString不包含乱码，则直接返回，否则进行解码处理.
     * @return 不存在乱码的字符串
     */
    public static String toChinese(final String pOriginalString) {
        if(pOriginalString == null){
            return null;
        }

        if(isChinese(pOriginalString)){
            return pOriginalString;
        }

        try{
            return new String(pOriginalString.getBytes("ISO-8859-1"), "gb2312");
        }catch(Exception ex){
            ex.printStackTrace();
        }

        return null;
    }

    /**
     * 判断一个字符串中是否有汉字 判断依据： 如果字符串pValue中存在值大于256的字符就认为有汉字，否
     * 则就没有（非汉字数据的整数值不可能大于256的，因为他只有8位）
     * @param pValue 要检查的字符串.
     * @return boolean
     * @pre pValue != null
     */
    public static boolean isChinese(String pValue) {
        for(int i = 0; i < pValue.length(); i++){
            if((int) pValue.charAt(i) > 256)
                return true;
        }
        return false;
    }

    /**
     * 根据指定的分割符分割字符串.
     * @param pStr 一特定的字符串，以某分隔符分隔.
     * @param delim 指定的分隔符.
     * @return List.
     * @author luohc 2004-7-27.
     */
    public static List splitStr(String pStr, String delim) {
        List list = new ArrayList();
        if(pStr == null || delim == null)
            return list;
        StringTokenizer st = new StringTokenizer(pStr, delim);
        while(st.hasMoreTokens()){
            list.add(st.nextToken());
        }
        return list;
    }

    /**
     * 根据指定的分割符分割字符串.
     * @param str 一特定的字符串，以某分隔符分隔.
     * @param delim 指定的分隔符.
     * @return 数组。str 为 null 时返回长度为0的数组。
     */
    public static String[] splitStr2(String str, String delim) {
        List list = splitStr(str, delim);
        if(list.isEmpty()){
            return new String[0];
        }else{
            String[] strs = new String[list.size()];
            for(int i = 0; i < list.size(); i++){
                strs[i] = (String) list.get(i);
            }
            return strs;
        }
    }

    /**
     * 把字符串数组转换成用分割符分割的字符串.
     * @param pStrArray 字符串数组.
     * @param delim 指定的分隔符.
     * @return String
     * @author zhouzh 2004-4-14.
     */
    public static String constructStr(String[] pStrArray, String delim) {
        if(pStrArray == null || pStrArray.length == 0){
            return "";
        }
        StringBuffer sb = new StringBuffer("");
        for(int i = 0; i < pStrArray.length; i++){
            if(i != 0){
                sb.append(delim);
            }
            sb.append(pStrArray[i]);
        }
        return sb.toString();
    }

    /**
     * 去除串前后空格，若为null，转换成长度为0的串.
     * @param pStr 原字符串.
     * @return 返回前后空格的字符串.
     * @author luohc 2004-7-27
     */
    public static String trim(String pStr) {
        return pStr == null ? "" : pStr.trim();
    }

    // 去掉所有空格
    public static String trimAllSpace(String s) {
        if(s == null) return null;
        return s.replaceAll(" ", "");
        /*
        StringBuffer result = new StringBuffer(128);
        for(int i=0; i<s.length(); i++){
            char ch = s.charAt(i);
            if(ch != ' '){
                result.append(ch);
            }
        }
        return result.toString(); */
    }
   
    public final static String xmlEncode(String s) {
		if (s == null) {
			return null;
		}
		StringBuffer str = new StringBuffer(1024);
		for (int j = 0; j < s.length(); j++) {
			char c = s.charAt(j);
			// encode standard ASCII characters into HTML entities where needed
			if (c < '\200') {
				switch (c) {
				case '&':
					str.append("&amp;");
					break;
				case '<':
					str.append("&lt;");
					break;
				case '>':
					str.append("&gt;");
					break;
				case '\'':
					str.append("&apos;");
					break;
				case '"':
					str.append("&quot;");
					break;
				default:
					str.append(c);
				}
			} else {
				str.append(c);
			}
		}
		return str.toString();
	}
    
    /**
     * 判断字符串是否为空. 如果字符串为null或者全为空格或者为“null”，都返回true.
     * @param pStr 要检查的字符串.
     * @return boolean 值.
     * @author luohc 2004-7-27
     */
    public static boolean isBlankStr(String pStr) {
        return pStr == null || pStr.trim().length() == 0
                || pStr.equalsIgnoreCase("null");
    }    

    /**
     * 判断字符串是否不为空。 字符串为null,或全是空格，或者是"null"，都返回false.
     * @param pStr
     * @return true/false. 不为空(null, " ", "null")时返回true.
     * @see isBlankStr(String).
     */
    public static boolean isNotBlank(String pStr) {
        return !isBlankStr(pStr);
    }
    
    /**
     * 省略字符串，只取字符串前面length个字符加上省略号返回.
     * @param pStr 字符串。
     * @param length 要保留的长度（英文字符长度，一个汉字占两个字符长度）。
     * @return 如果pStr的长度小于length，则返回原串。
     */
    public static String omit(String pStr, int length) {
        if(pStr == null || pStr.length() == 0 || length <= 0)
            return pStr;
        int len = 0;
        int count = 0; // 最终要返回的中英文字符个数.
        for(int i = 0; i < pStr.length(); i++){
            char c = pStr.charAt(i);
            // 转换成英文字符长度.
            if(c > 256){
                len += 2;
            }else{
                len += 1;
            }
            // 小于要返回的英文字符个数。
            if(len <= length){
                count++;
            }else{
                break;
            }
        }
        if(count >= pStr.length())
            return pStr;
        // 要去掉省略号的长度(1个字符).
        String str = pStr.substring(0, count - 1);
        return str + "...";
    }

    /**
     * 生成唯一序列号.
     */
    public static String getUUID() {
    	return null;
//        return new org.w3c.util.UUID().toString();
    }

    /**
     * 解析字符串为boolean值 yes, y, true, on, 1 都解析为true(不区分大小写)，否则解析为false
     * @param str 侍解析字符串
     * @return 解析结果
     */
    public static boolean parseBoolean(String str) {
        return ((str != null) && (str.equalsIgnoreCase("yes")
                || str.equalsIgnoreCase("y") || str.equalsIgnoreCase("true")
                || str.equalsIgnoreCase("on") || str.equalsIgnoreCase("1")));
    }

    /**
     * 用"<br>
     * "替换回车符和字符'\r\n'
     * @param orgStr 含有回车符的字符串。
     * @return 替换后的字符串.
     * @deprecated
     */
    public static String replaceEnter(String orgStr) {
        if(orgStr == null || orgStr.length() == 0)
            return orgStr;
        return orgStr.replaceAll("\r\n", "<br>");
    }

    /**
     * 用指定的字符替换回车符和字符'\r\n'
     * @param orgStr 含有回车符的字符串。
     * @param value 用来代替回车的字符
     * @return 替换后的字符串.
     */
    public static String replaceEnter(String orgStr, String value) {
        if(orgStr == null || orgStr.length() == 0)
            return orgStr;
        return orgStr.replaceAll("\r\n", value);
    }

    /**
     * 用指定的新字符穿代替指定的旧字符串
     * @param orgStr 要进行替换的字符串
     * @param oldValue 旧字符串
     * @param newValue 新字符串
     * @return
     * @deprecated
     */
    public static String replace(String orgStr, String oldValue,
            String newValue) {
        if(orgStr == null || orgStr.length() == 0)
            return orgStr;
        return orgStr.replaceAll(oldValue, newValue);
    }
    
    
    /**
     * 通过Collection构造sql in字符串
     * @param c
     * @return
     */
    public static String getInCondition(Collection c){        
        StringBuffer sb = new StringBuffer(256);  
        int flag = 0;
        if(c == null || c.isEmpty()){
            return null;  
        }
        for(Iterator it = c.iterator(); it.hasNext();){
            flag ++;
            if(flag == 1){
                sb.append("'" + it.next() + "'");
            }else{
                sb.append(",'" + it.next() + "'");  
            }  
        }    
        return sb.toString();  
    }
    
    /**
     * 为了能正确排序含有数字的字符串。需要将中间的数字前补０。
     * @param str　形如 ABC1 ->　ABC001
     * @param len 格式化的长度。
     * @return String.
     */
    public static String formatForOrder(String str, int len) {
        if(isBlankStr(str)) return null;
        int index = -1;
        for(int i=str.length()-1; i>=0; i--) {
            char c = str.charAt(i);
            if(c >= '0' && c <= '9') {
               index = i;             
            }else {
                break;
            }
        }
        if(index >= 0) {
            StringBuffer sb = new StringBuffer(16);
            sb.append(str.substring(0, index));
            String sNum = str.substring(index);
            for(int i=0; i<len-sNum.length(); i++) {
                sb.append("0");
            }
            sb.append(sNum);
            return sb.toString();
        }else {
            return str;
        }
    }
    
    /* 增加单引号(单字符) */
	public static String addSQM(String pValue) {
		if (!isBlankStr(pValue)) {
			return "'" + pValue + "'";
		}
		return "''";
	}
	
	/* 增加单引号(字符数组) */
	public static String addSQM(String[] ids) {
		if (judgeArray(ids)) {
			String result = "";
			for (int i = 0; i < ids.length; i++) {
				if (i > 0)
					result += ",";
				result += addSQM(ids[i]);
			}
			return result;
		}
		return "''";
	}
	
	/**
	 * 判断字符是否由startValue开头,endValue结束
	 */
	public static boolean isBetweenWith(String pValue, String startValue,
			String endValue) {
		if (pValue == null) {
			return false;
		}		
		if (pValue.startsWith(startValue) && pValue.endsWith(endValue)) {
			return true;
		}
		return false;
	}
	
	/**
	 * 判断字符是否真实数字
	 */
	public static boolean isRealNumber(String pValue) {
		try {
			new BigDecimal(pValue);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * 是否负数
	 * @deprecated
	 */
	public static boolean isNegative(String pValue){		
		if(pValue.startsWith("#") && pValue.lastIndexOf("#") == 0){
			return true;
		}
		return false;			
	}

	/**
	 * 判断是否操作符
	 */
	public static boolean isOperator(String pValue) {
		if(pValue != null && pValue.length() == 1 && "+-*/".indexOf(pValue) > -1) {
			return true;
		}
		//if ("+".equals(pValue) || "-".equals(pValue) || "*".equals(pValue)
		//		|| "/".equals(pValue)) {
		//	return true;
		//}
		return false;
	}
	
	/**
	 * 字符串及字符串数组(装载于List中)比较器
	 */
	class StringArrayComparator implements Comparator {

		boolean bAscSort = true;// 升降序

		int index = 0;// 下标

		public StringArrayComparator() {
		}

		public StringArrayComparator(boolean pAscSort, int pIndex) {
			this.bAscSort = pAscSort;
			this.index = pIndex;
		}

		public int compare(Object arg0, Object arg1) {
			if (arg0 == null)
				return -1;
			if (arg1 == null)
				return 1;

			String str1 = "";
			String str2 = "";

			if (arg0 instanceof String[] && arg1 instanceof String[]) {
				str1 = ((String[]) arg0)[index];
				str2 = ((String[]) arg1)[index];
			} else if (arg0 instanceof String && arg1 instanceof String) {
				str1 = (String) arg0;
				str2 = (String) arg1;
			}
			return bAscSort ? str1.compareTo(str2) : -str1.compareTo(str2);
		}
	}
	
	/**
	 * 将字符串反转输出
	 * @param pString
	 * @return
	 */
	public static String reverse(String pString) {
		StringBuffer temp = new StringBuffer(pString);		
		return temp.reverse().toString();
	}
	
	/**
	 * 某字符是否存在字符串
	 * @deprecated
	 */
	public static boolean isExist(String pValue, char pCh) {
		if (pValue.indexOf(pCh) != -1) {
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @param pAttributeName
	 * @param pValue
	 * @return
	 * @deprecated
	 */
	public static String getXMLString(String pAttributeName, String pValue) {
		if (isNotBlank(pValue)) {
			return "<" + pAttributeName + "><![CDATA[" + pValue + "]]></" + pAttributeName
					+ ">\n";
		}
		return "";
	}
	
	/**
	 * 判断字符是否为空 
	 * @param pValue
	 * @return
	 * @deprecated
	 */
	public static boolean judgeValue(String pValue) {
		return null != pValue && !"".equals(pValue.trim())
				&& !"null".equals(pValue.trim());
		// 最后一个判断是负责除去OA是的空值
	}
	
	/**
	 * 判断字符数组是否为空
	 * @param pArray
	 * @return
	 * @deprecated
	 */
	public static boolean judgeArray(String[] pArray) {
		return null != pArray && pArray.length > 0;
	}
	
	/**
	 * 日期变字符串（短）
	 * @param shortDate
	 * @return
	 * @deprecated
	 */
	public static String getStringDate(Date shortDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = "";
		try {
			dateString = formatter.format(shortDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dateString;
	}
	
	/**
	 * 判断集合是否为空 
	 * @param pCollection
	 * @return
	 * @deprecated
	 */
	public static boolean judgetCollection(Collection pCollection) {
		return null != pCollection && pCollection.size() > 0;
	}
	
	/**
	 * @param pTree 完整的树
	 * @param pTreeCodeMethodName 获取树节点的方法名
	 * @param pNameMethodName 获取显示值的方法名
	 * @param pLeafTreeCode 本节点的树节点
	 * @param pSeparator 分割符号
	 * @return 由指定树节点到树的根节点的完整路径
	 * @deprecated
	 */
	public static String getNodeFullPath(List pTree,
			String pTreeCodeMethodName, String pNameMethodName,
			String pLeafTreeCode, String pSeparator) {

		Map treeMap = getMapByObjectList(pTree, pTreeCodeMethodName,
				pNameMethodName);

		if (judgetMap(treeMap)) {
			StringBuffer fullPath = new StringBuffer(reverse(treeMap.get(
					pLeafTreeCode).toString()));
			String parentCode = pLeafTreeCode;

			while (true) {
				parentCode = getParentTreeCode(parentCode);
				if (!"0".equals(parentCode)) {
					if (null != treeMap.get(parentCode)) {
						fullPath.append(pSeparator);
						fullPath.append(reverse(treeMap.get(parentCode)
								.toString()));
					} else {
						break;
					}
				} else {
					break;
				}
			}
			return fullPath.reverse().toString();
		}
		return "";
	}
	
	/**
	 * 使用反射，将对象列表中的对象的两个属性，以键值形式存于Map中
	 * 
	 * @param pObjectList 对象列表
	 * @param pKeyMethod 获取存于键的属性的方法名称
	 * @param pValueMethod 获取存于值的属性的方法名称
	 * @return
	 * @deprecated
	 */
	public static Map getMapByObjectList(List pObjectList, String pKeyMethod,
			String pValueMethod) {
		HashMap map = new HashMap();
		if (judgetCollection(pObjectList)) {
			for (Iterator iter = pObjectList.iterator(); iter.hasNext();) {
				Object element = iter.next();
				try {
					String key = element.getClass().getMethod(pKeyMethod,
							new Class[] {}).invoke(element, null).toString();
					String value = element.getClass().getMethod(pValueMethod,
							new Class[] {}).invoke(element, null).toString();
					map.put(key, value);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return map;
	}
	
	/**
	 * 判断Map是否为空
	 * @param pMap
	 * @return
	 * @deprecated
	 */
	public static boolean judgetMap(Map pMap) {
		return null != pMap && !pMap.isEmpty();
	}
	
	/**
	 * @deprecated
	 */
	public static List sortStringArrayByComparator(List pList,
			boolean pAscSort, int pIndex) {
		Collections.sort(pList, new StrTool().new StringArrayComparator(
				pAscSort, pIndex));
		return pList;
	}
	
	/**
	 * 取出对象列表中每个对象的主键，构造ids字符串数组
	 * @param pObjectList 对象列表
	 * @param pMethodName 对象中的方法名称
	 * @return
	 * @deprecated
	 */
	public static String[] getObjectListIds(Collection pObjectList,
			String pMethodName) {
		if (judgetCollection(pObjectList)) {
			String[] result = new String[pObjectList.size()];
			int i = 0;
			for (Iterator iter = pObjectList.iterator(); iter.hasNext();) {
				Object element = (Object) iter.next();
				try {
					result[i++] = element.getClass().getMethod(pMethodName,
							new Class[] {}).invoke(element, null).toString();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return result;
		}
		return null;
	}
	
	/**
	 * 在参数map中的每个key的前面加上指定的前缀
	 * 
	 * @param pParam 参数map
	 * @param pPrefix 前缀
	 * @return
	 * @deprecated
	 */
	public static Map addPrefixToParam(Map pParam, String pPrefix) {
		if (judgetMap(pParam)) {
			Map result = new HashMap();
			for (Iterator iter = pParam.keySet().iterator(); iter.hasNext();) {
				String paramName = (String) iter.next();
				result.put(pPrefix + paramName, transactValue(pParam.get(
						paramName).toString()));
			}
			return result;
		}
		return new HashMap();
	}
	
	/**
	 * @deprecated
	 */
	public static String transactValue(String pValue) {
		return judgeValue(pValue) ? pValue : "";
	}
  
    /**
     * @deprecated
     */
    private static String getParentTreeCode(String treeCode) {
    	final int TREE_LENGTH = 5;
        if (StrTool.isBlankStr(treeCode))
            return null;
        if (treeCode.length() == TREE_LENGTH)
            return "0";
        return treeCode.substring(0, treeCode.length() - TREE_LENGTH);
    }
    
    /**
     *
     */
    public static String valueOf(Object obj) {
        return obj == null ? "" : String.valueOf(obj);
    }    
    
    /**
     * 将在javascript中具有特殊含义的字符替换为转义后字符
     * @param str 含有特殊字符的字符串。
     * @return 转义后的字符串.
     */
    public static String convertString4js(String str) {
    	if(str == null || str.length() == 0)
            return str;
		return str.replaceAll("'","\\\\'");
    }
    
}
