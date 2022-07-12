package it.polito.tdp.crimes.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.crimes.model.Adiacenza;
import it.polito.tdp.crimes.model.Event;


public class EventsDao {
	
	public List<Event> listAllEvents(){
		String sql = "SELECT * FROM events" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Event> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Event(res.getLong("incident_id"),
							res.getInt("offense_code"),
							res.getInt("offense_code_extension"), 
							res.getString("offense_type_id"), 
							res.getString("offense_category_id"),
							res.getTimestamp("reported_date").toLocalDateTime(),
							res.getString("incident_address"),
							res.getDouble("geo_lon"),
							res.getDouble("geo_lat"),
							res.getInt("district_id"),
							res.getInt("precinct_id"), 
							res.getString("neighborhood_id"),
							res.getInt("is_crime"),
							res.getInt("is_traffic")));
				} catch (Throwable t) {
					t.printStackTrace();
					System.out.println(res.getInt("id"));
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<String> getCategotyID(){
		String sql="select distinct offense_category_id "
				+ "from events "
				+ "order by offense_category_id";
		List<String> list= new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				list.add(res.getString("offense_category_id"));
				
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
		
	}
	public List<String> getDay(){
		String sql="select distinct date(reported_date) as day "
				+ "from events "
				+ "order by date(reported_date)";
		
		List<String> list= new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				list.add(res.getString("day"));
				
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	public List<String> getVertici(String category, String day){
		String sql="select distinct offense_type_id "
				+ "from events "
				+ "where offense_category_id=? and date(reported_date)=?";
		List<String> list= new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setString(1, category);
			st.setString(2, day);
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				
					list.add(res.getString("offense_type_id"));
				
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	public List<Adiacenza> getArchi(String category, String day){
		String sql="SELECT e1.offense_type_id AS ot1, e2.offense_type_id AS ot2, COUNT(DISTINCT e1.precinct_id) AS peso  "
				+ "FROM EVENTS AS e1, EVENTS AS e2 "
				+ "WHERE e1.offense_type_id > e2.offense_type_id "
				+ "AND e1.offense_category_id = ? "
				+ "AND e1.offense_category_id = e2.offense_category_id "
				+ "AND DATE(e1.reported_date) = ? "
				+ "AND DATE(e1.reported_date) = DATE(e2.reported_date) "
				+ "GROUP BY e1.offense_type_id, e2.offense_type_id "
				+ "HAVING COUNT(DISTINCT e1.precinct_id) > 0";
		
		List <Adiacenza> list= new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setString(1, category);
			st.setString(2, day);
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				list.add(new Adiacenza(res.getString("ot1"), res.getString("ot2"), res.getInt("peso")));
				
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
		
	}

}
