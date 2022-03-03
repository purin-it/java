package com.example.demo;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest API定義クラス
 */
@RestController
public class DemoRestController {

	/** ユーザーデータテーブル(user_data)アクセス用リポジトリ */
	@Autowired
	private UserDataRepository repository;
	
	/**
	 * ユーザーデータリストを取得する.
	 * @return ユーザーデータリスト
	 */
	@GetMapping("/users")
	public List<UserData> getAllUserData() {
		return repository.findAll();
	}
	
	/**
	 * 指定したIDをもつユーザーデータを取得する.
	 * @param id ID
	 * @return 指定したIDをもつユーザーデータ
	 */
	@GetMapping("/users/{id}")
	public UserData getOneUserData(@PathVariable long id) {
		Optional<UserData> userData = repository.findById(id);
		// 指定したIDをもつユーザーデータがあればそのユーザーデータを返す
		if(userData.isPresent()) {
			return userData.get();
		}
		// 指定したIDをもつユーザーデータがなければnullを返す
		return null;
	}
	
	/**
	 * 指定したユーザーデータを登録する.
	 * @param userData ユーザーデータ
	 * @return 登録したユーザーデータ
	 */
	@PostMapping("/users")
	public UserData saveUserData(@Valid @RequestBody UserData userData) {
		return repository.save(userData);
	}
	
	/**
	 * 指定したユーザーデータを更新する.
	 * @param id ID
	 * @param userData ユーザーデータ
	 * @return 更新したユーザーデータ
	 */
	@PutMapping("/users/{id}")
	public UserData updateUserData(@PathVariable long id, @Valid @RequestBody UserData userData) {
		userData.setId(id);
		return repository.save(userData);
	}
	
	/**
	 * 指定したIDをもつユーザーデータを削除する.
	 * @param id ID
	 */
	@DeleteMapping("/users/{id}")
	public void deleteUserData(@PathVariable long id) {
		Optional<UserData> userData = repository.findById(id);
		// 指定したIDをもつユーザーデータがあればそのユーザーデータを削除する
		if(userData.isPresent()) {
			repository.deleteById(id);
		}
	}
}
