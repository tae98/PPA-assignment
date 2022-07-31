
/*
 * Statistics class
 */

import java.util.ArrayList;
import java.util.HashMap;

public class Statistics {

	// neighbor hoods
	private HashMap<String, ArrayList<AirbnbListing>> neighborhoods;
	
	// properties
	private ArrayList<AirbnbListing> selected;

	// initialize
	public Statistics(HashMap<String, ArrayList<AirbnbListing>> neighborhoods, ArrayList<AirbnbListing> selected) {
		this.neighborhoods = neighborhoods;
		this.selected = selected;
	}

	// return number of properties
	public int getNumberProperties() {
		return selected.size();
	}

	// get average review Score
	public double getAverageReviewScore() {
		double sum = 0;
		int count = 0;

		// add up reviers
		for (AirbnbListing listing : selected) {
			if (listing.getNumberOfReviews() > 0) {
				sum += listing.getNumberOfReviews();
				count++;
			}
		}

		double average = 0;

		if (count > 0)
			average = sum / count;

		return average;
	}

	// total number of homes and apartments
	public int getTotalNumberOfHomesandApartments() {
		int count = 0;

		// add up type
		for (AirbnbListing listing : selected) {

			if (listing.getRoom_type().trim().equalsIgnoreCase("Entire Home/apt")) {

				count++;
			}
		}

		return count;
	}

	// priciest neighborhood
	public String pricestNeighborHood() {
		// store average prices
		HashMap<String, Double> averagePrices = new HashMap<String, Double>();
		int count = 0;
		int sum = 0;

		for (String neighborhood : neighborhoods.keySet()) {
			count = 0;
			sum = 0;

			for (AirbnbListing listing : neighborhoods.get(neighborhood)) {
				sum += listing.getPrice() * listing.getMinimumNights();
				count += listing.getMinimumNights();
			}

			double averagePrice = 0;

			if (count > 0)
				averagePrice = sum / count;

			// store average price
			averagePrices.put(neighborhood, averagePrice);
		}

		double maxAveragePrice = 0;
		String maxNeighborhood = "";

		for (String neighborhood : neighborhoods.keySet()) {
			if (averagePrices.get(neighborhood) > maxAveragePrice) {
				maxAveragePrice = averagePrices.get(neighborhood);
				maxNeighborhood = neighborhood;
			}
		}

		return maxNeighborhood;
	}

	// priciest neighborhood
	public String lowestNeighborHood() {
		// store average prices
		HashMap<String, Double> averagePrices = new HashMap<String, Double>();
		int count = 0;
		int sum = 0;

		for (String neighborhood : neighborhoods.keySet()) {
			count = 0;
			sum = 0;

			for (AirbnbListing listing : neighborhoods.get(neighborhood)) {
				sum += listing.getPrice() * listing.getMinimumNights();
				count += listing.getMinimumNights();
			}

			double averagePrice = 0;

			if (count > 0)
				averagePrice = sum / count;

			// store average price
			averagePrices.put(neighborhood, averagePrice);

		}

		double minAveragePrice = Integer.MAX_VALUE;
		String minNeighborhood = "";

		for (String neighborhood : neighborhoods.keySet()) {
			if (averagePrices.get(neighborhood) < minAveragePrice) {
				minAveragePrice = averagePrices.get(neighborhood);
				minNeighborhood = neighborhood;
			}
		}

		return minNeighborhood;
	}

	// return min price
	public int getMinPrice() {
		int minPrice = Integer.MAX_VALUE;

		for (String neighborhood : neighborhoods.keySet()) {

			for (AirbnbListing listing : neighborhoods.get(neighborhood)) {
				if (listing.getPrice() < minPrice)
					minPrice = listing.getPrice();
			}

		}

		return minPrice;
	}

	// return min price
	public int getMaxPrice() {
		int maxPrice = 0;

		for (String neighborhood : neighborhoods.keySet()) {

			for (AirbnbListing listing : neighborhoods.get(neighborhood)) {
				if (listing.getPrice() > maxPrice)
					maxPrice = listing.getPrice();
			}

		}

		return maxPrice;
	}

	// get number of neighbor hoods
	public int getNumberOfNeighborHoods() {
		return neighborhoods.size();
	}

}
