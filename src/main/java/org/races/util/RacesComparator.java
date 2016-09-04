package org.races.util;

import java.util.Comparator;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

import org.races.model.PendingService;

public class RacesComparator implements Comparator<PendingService> {

	@Override
	public int compare(PendingService ps1, PendingService ps2) {
		// TODO Auto-generated method stub
		return ps1.getTaluk().compareTo(ps2.getTaluk()); 
	}

	@Override
	public Comparator<PendingService> reversed() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Comparator<PendingService> thenComparing(
			Comparator<? super PendingService> other) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <U> Comparator<PendingService> thenComparing(
			Function<? super PendingService, ? extends U> keyExtractor,
			Comparator<? super U> keyComparator) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <U extends Comparable<? super U>> Comparator<PendingService> thenComparing(
			Function<? super PendingService, ? extends U> keyExtractor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Comparator<PendingService> thenComparingInt(
			ToIntFunction<? super PendingService> keyExtractor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Comparator<PendingService> thenComparingLong(
			ToLongFunction<? super PendingService> keyExtractor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Comparator<PendingService> thenComparingDouble(
			ToDoubleFunction<? super PendingService> keyExtractor) {
		// TODO Auto-generated method stub
		return null;
	}

	 

}
