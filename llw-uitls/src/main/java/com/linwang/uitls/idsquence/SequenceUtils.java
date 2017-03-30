package com.linwang.uitls.idsquence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.sql.DataSource;

import com.linwang.uitls.IDGenerator;

public class SequenceUtils {
	 private static final Lock lock = new ReentrantLock();

	    private static ConcurrentMap<String, SequenceRange> sequenceRangeMap = new ConcurrentHashMap<String, SequenceRange>();

	    private static DataSource dataSource;
	    public static void setDataSource(DataSource dataSource) {
	        SequenceUtils.dataSource = dataSource;
	    }

	    public static long nextValue(String name) throws SQLException {
	    	if(dataSource == null){
	    		throw new RuntimeException("dataSource is null,Please set SequenceUtils.setDataSource(DataSource dataSource) value!");
	    	}
	        if (!sequenceRangeMap.containsKey(name)) {
	            lock.lock();
	            try {
	                sequenceRangeMap.putIfAbsent(name, nextRange(name));
	            } finally {
	                lock.unlock();
	            }
	        }

	        long value = sequenceRangeMap.get(name).getAndIncrement();
	        if (value == -1) {
	            lock.lock();
	            try {
	                for (;;) {
	                    if (sequenceRangeMap.get(name).isOverflow()) {
	                        sequenceRangeMap.replace(name, nextRange(name));
	                    }
	                    value = sequenceRangeMap.get(name).getAndIncrement();
	                    if (value == -1) {
	                        continue;
	                    }
	                    break;
	                }
	            } finally {
	                lock.unlock();
	            }
	        }

	        return value;
	    }
	    
	    public static SequenceRange nextRange(String name) throws SQLException {
	        if (name == null) {
	            throw new IllegalArgumentException("Sequence name is required");
	        }

	        int retryTimes = 32;
	        int step = IDGenerator.getOrderIdStep();

	        for (int i = 0; i < retryTimes; ++i) {
	        	Connection conn = dataSource.getConnection();
	        	conn.setAutoCommit(true);
	        	PreparedStatement ps =  conn.prepareStatement("SELECT value FROM idcreater WHERE name = ?");
	            ps.setString(1, name);
	            ResultSet rs = ps.executeQuery();
	            long oldValue=0;
	            if(rs.next()){
	            	oldValue = rs.getLong("value");
	            }else{
	            	rs.close();
	                ps.close();
	            	conn.close();
	            	throw new RuntimeException("Sequence name can not find, name = " + name);
	            }
	            rs.close();
	            ps.close();
	            if (oldValue < 0 || oldValue > 0x6fffffffffffffffL) {
	            	conn.close();
	                throw new RuntimeException("Sequence value is incorrect, value = " + oldValue);
	            }

	            long newValue = oldValue + step;
	            ps =  conn.prepareStatement("update idcreater set value = ?,updatedAt=now() "
	            		+ "where name=? and value=?");
	            ps.setLong(1, newValue);
	            ps.setString(2, name);
	            ps.setLong(3, oldValue);
	            int efectRows = ps.executeUpdate();
	            ps.close();
	            conn.close();
	            if (efectRows == 0) {
	                continue;
	            }
	            
	            return new SequenceRange(oldValue + 1, newValue);
	        }

	        throw new RuntimeException("Retried too many times, retryTimes = " + retryTimes);
	    }

	    private SequenceUtils() {
	    }
}
