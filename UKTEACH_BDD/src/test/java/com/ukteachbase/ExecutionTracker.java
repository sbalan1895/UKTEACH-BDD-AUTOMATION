package com.ukteachbase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ExecutionTracker {
	
	// every method that MUST run in the scenario
		private static final List<String> EXPECTED = Arrays.asList(
				"navigateToUKTeach", "enterPassCode", "handleCookiesPopup",
				"enterKeyword", "clkSearchBtn", "getJobCount",
				"getTopJob", "getJobList");

		private static final Map<String, String> LOG = new LinkedHashMap<>();

		public static void reset() { LOG.clear(); }

		public static void pass(String method) { LOG.put(method, "PASSED"); }

		public static void fail(String method, String reason) { LOG.put(method, "FAILED - " + reason); }

		public static List<String> notExecuted() {
			List<String> missing = new ArrayList<>();
			for (String m : EXPECTED) {
				if (!"PASSED".equals(LOG.get(m))) {
					missing.add(m + " -> " + LOG.getOrDefault(m, "NOT EXECUTED"));
				}
			}
			return missing;
		}

		public static String summary() {
			StringBuilder sb = new StringBuilder("\n===== Execution Summary =====\n");
			for (String m : EXPECTED) {
				sb.append(String.format("%-22s : %s%n", m, LOG.getOrDefault(m, "NOT EXECUTED")));
			}
			return sb.toString();
		}

}
