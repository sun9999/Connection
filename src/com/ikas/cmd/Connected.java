package com.ikas.cmd;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

public class Connected {
    private Map<String, Set<String>> dataMap = null;

    public Map<String, Set<String>> getDataMap() {
        return dataMap;
    }

    public void readFile(String filename) {	
	if (StringUtils.isBlank(filename)) {
	    throw new IllegalArgumentException("The filename is blank!");
	}
	
	dataMap = new HashMap<>();
	
	try (BufferedReader bufReader = new BufferedReader(new FileReader(filename))) {
	    String line=null;
	    
	    while ((line=bufReader.readLine())!=null) {
		if (StringUtils.isBlank(line)) {continue;}
		String[] names = line.split(",");
		if (names==null) {continue;}
		if (names.length<2) {continue;}
		for (int i=0; i<2; i++) {
		    if (dataMap.get(names[i].trim())==null) {
			dataMap.put(names[i].trim(), new HashSet<String>());
		    }
		}
		dataMap.get(names[0].trim()).add(names[1].trim());		
		dataMap.get(names[1].trim()).add(names[0].trim());
	    }
	} catch (IOException e) {
	    throw new RuntimeException(e);
	}
    }
    
    public boolean isExistInDataset(String data) {
	if (dataMap==null || dataMap.isEmpty()) {return false;}
	if (data==null) {throw new IllegalArgumentException("data is null!");}
	
	return dataMap.keySet().contains(data);
    }
    
    public String oneWaySearch(String start, String destination) {
	if (start==null) {throw new IllegalArgumentException("start is null!");}
	if (destination==null) {throw new IllegalArgumentException("destination is null!");}
	if (dataMap==null || dataMap.isEmpty()) {return null;}
	
	if (!isExistInDataset(start)) {return null;}
	if (!isExistInDataset(destination)) {return null;}
	
	Queue<String> queue1 = new LinkedList<>();
	List<String> visitedItems1 = new ArrayList<>();
	
	visitedItems1.add(start);
	queue1.add(start);
	
	while (!queue1.isEmpty()) {
	    String result = breadthFirstTraversal(queue1, visitedItems1, destination);
	    
	    if (result!=null) {
		return result;
	    }
	}
	
	return null;
    }
    
    public String biDirectionalSearch(String start, String destination) {
	if (start==null) {throw new IllegalArgumentException("start is null!");}
	if (destination==null) {throw new IllegalArgumentException("destination is null!");}
	if (dataMap==null || dataMap.isEmpty()) {return null;}
	
	if (!isExistInDataset(start)) {return null;}
	if (!isExistInDataset(destination)) {return null;}
	
	Queue<String> queue1 = new LinkedList<>();
	Queue<String> queue2 = new LinkedList<>();
	
	List<String> visitedItems1 = new ArrayList<>();
	List<String> visitedItems2 = new ArrayList<>();
	
	queue1.add(start);
	queue2.add(destination);
	
	visitedItems1.add(start);
	visitedItems2.add(destination);
	
	while (!queue1.isEmpty() && !queue2.isEmpty()) {
	    String result;
	    if (queue1.size() <= queue2.size()) {
		result = breadthFirstTraversal(queue1, visitedItems1, destination);
	    } else {
		result = breadthFirstTraversal(queue2, visitedItems2, start);
	    }
	    if (result!=null) {
		return result;
	    }	    
	}
	
	return null;
    }
    
    private String breadthFirstTraversal(Queue<String> queue, List<String> visitedItems, String destination) {
	String qItem = queue.poll();
	if (qItem==null) {return null;}
	Set<String> links = dataMap.get(qItem);
	if (links==null) {return null;}
	for (String link: links) {
	    if (link.equals(destination)) {return link;}
		if (!visitedItems.contains(link)) {
		    visitedItems.add(link);
		    queue.add(link);
		}
	}
	return null;
    }
    
    public void printUsage() {
	System.out.println("Usage: Connected [filename] [source] [destination]");
    }
    
    public static void main(String args[]) {
	Connected conn = new Connected();
	
	if (args==null || args.length<3) {
	    conn.printUsage();
	    return;
	}
	
	conn.readFile(args[0]);
	
	if (conn.biDirectionalSearch(args[1], args[2])==null) {
	    System.out.println("no");
	} else {
	    System.out.println("yes");
	}
    }
}
