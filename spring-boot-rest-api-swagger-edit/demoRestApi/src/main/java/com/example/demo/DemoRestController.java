package com.example.demo;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Rest API定義クラス
 */
@Api(tags="OperateUserData")
@RestController
public class DemoRestController {

	/** ユーザーデータテーブル(user_data)アクセス用リポジトリ */
	@Autowired
	private UserDataRepository repository;
	
	/**
	 * ユーザーデータリストを取得する.
	 * @return ユーザーデータリスト
	 */
	@ApiOperation(value = "全ユーザー情報の取得", notes="登録されている全てのユーザー情報を取得します")
	@ApiResponses(value = {
		@ApiResponse(code=200, message="取得した全てのユーザー情報")
	})
	@GetMapping("/users")
	public List<UserData> getAllUserData() {
		return repository.findAll();
	}
	
	/**
	 * 指定したIDをもつユーザーデータを取得する.
	 * @param id ID
	 * @return 指定したIDをもつユーザーデータ
	 */
	@ApiOperation(value = "指定したユーザー情報の取得", notes="指定したIDをもつユーザー情報を取得します")
	@ApiResponses(value = {
		@ApiResponse(code=200, message="取得したユーザー情報")
	})
	@GetMapping("/users/{id}")
	public UserData getOneUserData(@ApiParam(name="id", required=true, value="取得するID") @PathVariable long id) {
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
	@ApiOperation(value = "指定したユーザー情報の登録", notes="指定したユーザー情報を登録します")
	@ApiResponses(value = {
		  @ApiResponse(code=200, message="登録したユーザー情報")
		, @ApiResponse(code=400, message="指定したユーザー情報のデータ不正")
	})
	@PostMapping("/users")
	public UserData saveUserData(
			@ApiParam(name="userData", required=true, value="登録するユーザー情報") @Valid @RequestBody UserData userData) {
		return repository.save(userData);
	}
	
	/**
	 * 指定したユーザーデータを更新する.
	 * @param id ID
	 * @param userData ユーザーデータ
	 * @return 更新したユーザーデータ
	 */
	@ApiOperation(value = "指定したユーザー情報の更新", notes="指定したユーザー情報を更新します")
	@ApiResponses(value = {
		  @ApiResponse(code=200, message="更新したユーザー情報")
		, @ApiResponse(code=400, message="指定したユーザー情報のデータ不正")
	})
	@PutMapping("/users/{id}")
	public UserData updateUserData(@ApiParam(name="id", required=true, value="更新するID") @PathVariable long id
			, @ApiParam(name="userData", required=true, value="更新後のユーザー情報") @Valid @RequestBody UserData userData) {
		userData.setId(id);
		return repository.save(userData);
	}
	
	/**
	 * 指定したIDをもつユーザーデータを削除する.
	 * @param id ID
	 */
	@ApiOperation(value = "指定したユーザー情報の削除", notes="指定したIDをもつユーザー情報を削除します")
	@DeleteMapping("/users/{id}")
	public void deleteUserData(@ApiParam(name="id", required=true, value="削除するID") @PathVariable long id) {
		Optional<UserData> userData = repository.findById(id);
		// 指定したIDをもつユーザーデータがあればそのユーザーデータを削除する
		if(userData.isPresent()) {
			repository.deleteById(id);
		}
	}
	
	/**
	 * 指定したユーザーデータを登録し、登録したデータを取得するURLを返却する.
	 * @param userData ユーザーデータ
	 * @return 登録したデータとそれを取得するURLを含むエンティティモデル
	 */
	@ApiOperation(value = "指定したユーザー情報の登録・取得", notes="指定したユーザー情報を登録し、登録したユーザー情報を取得するURLを返却します。")
	@ApiResponses(value = {
	      @ApiResponse(code=200, message="登録したユーザー情報とそれを取得するURL")
		, @ApiResponse(code=400, message="指定したユーザー情報のデータ不正")
	})
	@PostMapping("/users/hateoas")
	public EntityModel<UserData> saveUserDataHateoas(
			@ApiParam(name="userData", required=true, value="登録するユーザー情報") @Valid @RequestBody UserData userData) {
		UserData addUserData = repository.save(userData);
		if(addUserData == null) {
			return null;
		}
		EntityModel<UserData> entityModel = EntityModel.of(addUserData);
		WebMvcLinkBuilder linkTo = 
				linkTo(methodOn(this.getClass()).getOneUserData(addUserData.getId()));
		entityModel.add(linkTo.withRel("getAddUserDataUrl"));
		return entityModel;
	}
}
