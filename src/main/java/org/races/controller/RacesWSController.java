package org.races.controller;

import org.apache.log4j.Logger;
import org.races.service.RacesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.Gson;

@Controller
@RequestMapping("/movie")
public class RacesWSController {
	@Autowired
	RacesService racesService;
	private static Logger log = Logger.getLogger(RacesWSController.class);

	@RequestMapping(value = "/fetchTotalWorkingDays", method = RequestMethod.GET)
	public String getMovie(@RequestParam("startDate") String startDate,
			@RequestParam("endDate") String endDate) {
		Gson gson = new Gson();
		log.info("fetchTotalWorkingDays api called ... : startDate : "
				+ startDate + " endDate : " + endDate);
		int noOfWorkingDays = racesService.getNoOfWorkingDays(startDate,
				endDate);
		System.out.println("No Of Working Days : "+noOfWorkingDays);
		System.out.println(gson.toJson(noOfWorkingDays+""));
		return gson.toJson(Integer.parseInt(noOfWorkingDays+""));
	}
}
