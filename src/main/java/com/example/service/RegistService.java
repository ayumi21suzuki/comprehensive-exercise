package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Regist;
import com.example.domain.User;
import com.example.repository.RegistRepository;

@Service
@Transactional
public class RegistService {

	@Autowired
	private RegistRepository registRepository;

	public void insert(Regist regist) {
		registRepository.insert(regist);
	}

	// メールアドレス登録のため、regist_urlテーブルからメールアドレスを探す
	public List<Regist> findByEmail(String user_email) {
		return registRepository.findByEmail(user_email);
	}

	// メールアドレス登録のため、rusersテーブルからメールアドレスを探す
	public List<Regist> findByEmail2(String user_email) {
		return registRepository.findByEmail2(user_email);
	}

	public void insert(User user) {
		registRepository.insert(user);

	}

	// 本登録のため、regist_urlテーブルからメールアドレスを探す
	public List<Regist> findByEmail3(String key) {
		return registRepository.findByEmail3(key);

	}

	public void update(String key) {
		registRepository.update(key);

	}
	
} 