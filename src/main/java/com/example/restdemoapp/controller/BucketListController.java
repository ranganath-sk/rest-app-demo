package com.example.restdemoapp.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.restdemoapp.model.BucketList;

@RestController
public class BucketListController {

	private List<BucketList> bucketLists = new ArrayList<>();
	private final AtomicLong counter = new AtomicLong();

	public BucketListController() {

		bucketLists.add(new BucketList(counter.incrementAndGet(), "Random Data"));
	}

	@GetMapping(value = "/")
	public ResponseEntity index() {
		return ResponseEntity.ok(bucketLists);
	}

	@GetMapping(value = "/bucket")
	public ResponseEntity getBucket(@RequestParam(value = "id") Long id) {

		BucketList itemToReturn = null;

		for (BucketList bucketList : bucketLists) {
			if (bucketList.getId() == id) {
				itemToReturn = bucketList;
			}
		}
		return ResponseEntity.ok(itemToReturn);
	}

	@PostMapping(value = "/")
	public ResponseEntity addToBucketList(@RequestParam(value = "name") String name) {

		bucketLists.add(new BucketList(counter.incrementAndGet(), name));

		return ResponseEntity.ok(bucketLists);

	}

	@PutMapping(value = "/")
	public ResponseEntity updateBucketList(@RequestParam(value = "name") String name,
			@RequestParam(value = "id") Long id) {

		bucketLists.forEach(bucketList -> {

			if (bucketList.getId() == id) {
				bucketList.setName(name);
			}
		});

		return ResponseEntity.ok(bucketLists);
	}

	@DeleteMapping(value = "/")
	public ResponseEntity deleteBucketList(@RequestParam(value = "id") Long id) {

		BucketList itemDeleted = null;

		itemDeleted = bucketLists.stream().filter(bucketList -> bucketList.getId() == id).findFirst().get();
		bucketLists.remove(itemDeleted);

		return ResponseEntity.ok(itemDeleted);
	}
}
