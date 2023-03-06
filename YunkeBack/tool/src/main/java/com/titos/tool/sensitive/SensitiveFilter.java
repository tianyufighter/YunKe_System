package com.titos.tool.sensitive;

import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Titos
 */
@Component
public class SensitiveFilter {

    /**
     * 记录日志
     */
    private static final Logger logger = LoggerFactory.getLogger(SensitiveFilter.class);

    /**
     * 替换符
     */
    private static final String REPLACEMENT = "***";

    /**
     * 初始化根节点
     */
    private TrieNode rootNode = new TrieNode();

    /**
     * 2. 根据敏感词，初始化前缀树
     * @PostConstruct 当容器在服务器启动时实例化此Bean，调用Bean的构造方法后，该方法就会被自动调用
     */
    @PostConstruct
    public void init() {
        try (
                // 加载敏感词文件 sensitive-words.txt是自建的存放敏感词的文件
                InputStream is = this.getClass().getClassLoader().getResourceAsStream("sensitive_words.txt");
                // 字节流 -->  字符流 --> 字符缓冲流
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        ) {
            String keyword;
            while((keyword = reader.readLine()) != null){
                // 添加到前缀树，addKeyword为自定义的方法，将一个敏感词添加到前缀树中去
                this.addKeyword(keyword);
            }

        } catch (IOException e) {
            logger.error("加载敏感词文件失败:" + e.getMessage());
        }
    }

    /**
     * 封装方法：将一个敏感词添加到前缀树中去
     * @param keyword
     */
    private void addKeyword(String keyword){
        TrieNode tempNode = rootNode;
        for (int i = 0; i < keyword.length(); i++) {
            char c = keyword.charAt(i);
            TrieNode subNode = tempNode.getSubNode(c);

            if(subNode == null){
                // 如果子节点中没有该字符，则以此字符初始化子节点，并装配到树中
                subNode = new TrieNode();
                tempNode.addSubNode(c,subNode);
            }

            // 指向字节点，进入下一层循环
            tempNode = subNode;

            // 设置结束标识
            if(i == keyword.length() -1){
                tempNode.setKeywordEnd(true);
            }
        }
    }

    /**
     * 3. 检索并过滤敏感词
     * @param text 待过滤的文本
     * @return 过滤后的文本
     */
    public String filter(String text){
        if(StringUtils.isBlank(text)){
            return null;
        }

        // 指针①
        TrieNode tempNode = rootNode;
        // 指针②
        int begin = 0;
        // 指针③
        int position = 0;
        // 存放结果
        StringBuilder sb = new StringBuilder();

        while(position < text.length()){
            char c = text.charAt(position);

            // 跳过符号
            if(isSymbol(c)){
                // 若指针①处于根节点，将此符号计入结果，让指针②向下走一步
                if(tempNode == rootNode){
                    sb.append(c);
                    begin++;
                }
                // 无论符号在未检测时出现还是正在检测时出现，指针③总是向下走一步
                // （未检测时和指针②一起向下走一步，检测时指针②不动，指针③向下走一步）
                position++;
                continue;
            }

            // 检查下级节点
            tempNode = tempNode.getSubNode(c);
            if(tempNode == null){
                // 以begin开头的字符串不是敏感词
                sb.append(text.charAt(begin));
                // 进入下一个位置
                begin++;
                position = begin;
                // 指针①归位，重新指向根节点
                tempNode = rootNode;
            }else if (tempNode.isKeywordEnd()){
                // 发现敏感词，将begin~position字符串替换掉
                sb.append(REPLACEMENT);
                // 进入下一个位置
                position++;
                begin = position;
                // 指针①归位，重新指向跟接待你
                tempNode = rootNode;
            }else {
                // 检查下一个字符
                position++;
            }
        }
        // 将最后一批字符计入结果：指针③比指针②先到中终点，且两者之间的字符串不是敏感词
        sb.append(text.substring(begin));

        return sb.toString();
    }

    /**
     * 封装方法：判断是否为特殊符号
     * @param c
     * @return
     */
    private boolean isSymbol(Character c){
        // 0x2E80~0x9FFF 是东亚文字范围，不予当作特殊符号看待
        return !CharUtils.isAsciiAlphanumeric(c) && (c < 0x2E80 || c > 0x9FFF);
    }

    /**
     * 1. 定义前缀树
     */
    private class TrieNode {

        // 敏感词（关键词）结束标识
        private boolean isKeywordEnd = false;

        // 子节点（key是下级字符，value是下级节点）
        private Map<Character, TrieNode> subNodes = new HashMap<>();

        public boolean isKeywordEnd() {
            return isKeywordEnd;
        }

        public void setKeywordEnd(boolean keywordEnd) {
            isKeywordEnd = keywordEnd;
        }

        // 添加子节点
        public void addSubNode(Character c, TrieNode node) {
            subNodes.put(c, node);
        }

        // 获取子节点
        public TrieNode getSubNode(Character c) {
            return subNodes.get(c);
        }
    }

}