package com.scsa.memo.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scsa.memo.dto.Memo;
import com.scsa.memo.service.MemoService;

import io.swagger.annotations.ApiOperation;

//http://localhost:9999/swagger-ui.html
@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/api/memo")
public class MemoController {

	private static final Logger logger = LoggerFactory.getLogger(MemoController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	@Autowired
	private MemoService memoService;

	@ApiOperation(value = "모든 메모의 정보를 반환한다.", response = List.class)
	@GetMapping
	public ResponseEntity<List<Memo>> retrieveMemo() throws Exception {
		logger.info("retrieveMemo called - ");
		return new ResponseEntity<List<Memo>>(memoService.retrieveMemo(), HttpStatus.OK);
	}

	@ApiOperation(value = "입력한 ID에 해당하는 메모의 정보를 반환한다.", response = Memo.class)
	@GetMapping("/{id}")
	public ResponseEntity<Memo> detailMemo(@PathVariable int id) {
		logger.info("detailMemo  called - " + id);

		return new ResponseEntity<Memo>(memoService.detailMemo(id), HttpStatus.OK);
	}

	@ApiOperation(value = "새로운 메모 정보를 입력한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PostMapping
	public ResponseEntity<String> writeMemo(@RequestBody Memo memo) {
		logger.info("writeMemo called - " + memo);
		if (memoService.writeMemo(memo)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}

	@ApiOperation(value = "메모 정보를 수정한다. 그리고 DB수정 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PutMapping
	public ResponseEntity<String> updateMemo(@RequestBody Memo memo) {
		logger.info("updateMemo called - " + memo);

		if (memoService.updateMemo(memo)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}

	@ApiOperation(value = "id에 해당하는 메모를 삭제한다. 그리고 DB삭제 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteMemo(@PathVariable int id) {
		logger.info("deleteMemo called - " + id);

//        if (true) {
		if (memoService.deleteMemo(id)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}

}