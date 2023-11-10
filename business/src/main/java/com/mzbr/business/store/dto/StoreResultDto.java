package com.mzbr.business.store.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StoreResultDto {
	private int took;
	private boolean timed_out;
	private Shards shards;
	private Hits hits;

	// getters and setters

	public static class Shards {
		private int total;
		private int successful;
		private int skipped;
		private int failed;

		// getters and setters
	}

	@Getter
	public static class Hits {
		private Total total;
		private float max_score;
		private Hit[] hits;

		// getters and setters
	}

	public static class Total {
		private int value;
		private String relation;

		// getters and setters
	}

	@Getter
	public static class Hit {
		private String _index;
		private String _type;
		private String _id;
		private float _score;
		private Source _source;

		// getters and setters
	}

	@Getter
	public static class Source {
		private String _class;
		private int id;
		private String name;
		private String address;
		private Location location;

		// getters and setters
	}

	public static class Location {
		private double lat;
		private double lon;

		// getters and setters
	}
}
