package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import com.example.domain.Regist;
import com.example.domain.User;

@Repository
public class RegistRepository {
	@Autowired
	private NamedParameterJdbcTemplate template;

	private static final RowMapper<Regist> REGIST_ROW_MAPPER = (rs, i) -> {
		Regist regist = new Regist();
		regist.setUser_email(rs.getString("user_email"));
		return regist;
	};

	public void insert(Regist regist) {
		String insertSql = "INSERT INTO regist_url(user_email, unique_url) VALUES(:user_email, :unique_url);";
		SqlParameterSource param = new BeanPropertySqlParameterSource(regist);
		template.update(insertSql, param);
	}

	public List<Regist>findByEmail(String user_email){
		String sql = "SELECT * FROM regist_url WHERE user_email=:user_email";
		SqlParameterSource param = new MapSqlParameterSource().addValue("user_email", user_email);
		List<Regist> registList = template.query(sql, param, REGIST_ROW_MAPPER);

			if (registList.size() == 0) {
					return null;
				}
				return registList;
	}
	public List<Regist>findByEmail2(String user_email){
		String sql = "SELECT * FROM users WHERE user_email=:user_email";
		SqlParameterSource param = new MapSqlParameterSource().addValue("user_email", user_email);
		List<Regist> registList = template.query(sql, param, REGIST_ROW_MAPPER);

			if (registList.size() == 0) {
					return null;
				}
				return registList;
	}

	private static final RowMapper<User> USER_ROW_MAPPER = (rs, i) -> {
		User user = new User();
		user.setName(rs.getString("name"));
		user.setRuby(rs.getString("ruby"));
		user.setZipcode(rs.getString("zipcode"));
		user.setAddress(rs.getString("address"));
		user.setTelephone(rs.getString("telephone"));
		user.setPassword(rs.getString("password"));
		user.setDel_flg(rs.getInt(0));
		return user;
	};

	public void insert(User user) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		SqlParameterSource param = new BeanPropertySqlParameterSource(user);
		String insertSql = "INSERT INTO users(name,ruby,user_email,zipcode,address,telephone,password)"
				+ " VALUES(:name,:ruby,:user_email,:zipcode,:address,:telephone,:password);";
		template.update(insertSql, param);
	}

	public List<Regist> findByEmail3(String unique_url) {
		String sql = "SELECT * FROM regist_url WHERE unique_url=:unique_url AND regist_date +cast('1 days' as interval)>now();";
		SqlParameterSource param = new MapSqlParameterSource().addValue("unique_url", unique_url);
		List<Regist> registList = template.query(sql, param, REGIST_ROW_MAPPER);
		if (registList.size() == 0) {
			return null;
		}
		return registList;
	}
	public void update(String key) {
		String updateSql = "UPDATE regist_url SET del_flg=1 WHERE unique_url=:unique_url";
		SqlParameterSource param= new MapSqlParameterSource().addValue("unique_url",key);
		template.update(updateSql, param);

	}

}