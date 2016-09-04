package org.races.dao;

import java.sql.PreparedStatement; 
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

import org.apache.log4j.Logger;
import org.races.Constants.ReportConstants;
import org.races.model.AssociateDaDetails;
import org.races.model.AssociateSalaryDetails;
import org.races.model.DaDetails;
import org.races.model.EmployeeDetails;
import org.races.model.EnquiryDetails;
import org.races.model.FeedbackDetails;
import org.races.model.FeedbackUiDetails;
import org.races.model.JobCardDetails;
import org.races.model.LeaveDetails;
import org.races.model.MMDetails;
import org.races.model.OfferSheetDetails;
import org.races.model.PendingService;
import org.races.model.ReportFilter;
import org.races.model.SalaryDetails;
import org.races.model.ServiceActual;
import org.races.model.ServiceUsageTrendChart;
import org.races.model.SoldSpares;
import org.races.model.SparesCountMetrics;
import org.races.model.SparesDetails;
import org.races.model.SparesDiffReport;
import org.races.model.SparesSaleBySE;
import org.races.model.UserDetails;
import org.races.model.CustomerDetails;
import org.races.model.ValidityCheck;
import org.races.model.serviceDetails;
import org.races.model.spares_details; 
import org.races.util.DateUtil;
import org.races.util.ExportFilterValue;
import org.races.util.QueryList; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.races.util.ReportUtil;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.mysql.fabric.xmlrpc.base.Array;

public class RacesDaoImpl implements RacesDao {

	@Autowired
	JdbcTemplate jdbctemp;
	@Autowired
	Dao_Constants dao_constants; 
	@Autowired
	QueryList queryList;
	@Autowired
	ReportUtil reportUtil;
	@Autowired
	private PlatformTransactionManager transactionManager;
	@Autowired
	private DateUtil dateUtil;
	@Autowired  
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate; 
	
	SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
	// 05/2016
	SimpleDateFormat monthYear = new SimpleDateFormat("MM/yyyy");
	SimpleDateFormat queryFormat = new SimpleDateFormat("dd/MM/yyyy");
	SimpleDateFormat simpleDateformatYear = new SimpleDateFormat("yyyy");
	SimpleDateFormat simpleDateformatMonth = new SimpleDateFormat("MM");
	private static Logger log = Logger.getLogger(RacesDaoImpl.class);

	public UserDetails getLoginDetails(String userName, String password) {
		final UserDetails userdetail = new UserDetails();
		try {
			if (jdbctemp != null) {
				jdbctemp.queryForObject(dao_constants.GET_LOGIN_DETAILS,
						new Object[] { userName, password },
						new RowMapper<UserDetails>() {

							@Override
							public UserDetails mapRow(ResultSet rs, int count)
									throws SQLException {
								userdetail.setUserName(rs.getString("userName"));
								userdetail.setPassword(rs.getString("password"));
								userdetail.setEmail_id((rs
										.getString("email_id")));
								userdetail.setLoginType((rs
										.getString("LoginType")));
								userdetail.setEid(rs.getInt("eid"));
								// TODO Auto-generated method stub
								return userdetail;
							}

						});
			} else {
				return null;
			}
		} catch(org.springframework.jdbc.CannotGetJdbcConnectionException ex){
			System.out.println("Could Not Connect To Database !!!");
			log.debug("Could Not Connect To Database !!! :" + ex);
			userdetail.setErrorMessage("Could Not Connect To Database !!!");
			ex.printStackTrace();
		}catch (Exception ex) {
			System.out.println("NO USER FOUND !!!!");
			log.debug("NO USER FOUND : " + ex);
			 userdetail.setErrorMessage("No User Found");
			return userdetail;
		}
		return userdetail;
	}

	public UserDetails getEmailId(String userName) {
		final UserDetails userdetail = new UserDetails();
		try {
			if (jdbctemp != null) {
				jdbctemp.queryForObject(dao_constants.GET_LOGIN_DETAILS,
						new Object[] { userName },
						new RowMapper<UserDetails>() {

							@Override
							public UserDetails mapRow(ResultSet rs, int count)
									throws SQLException {
								userdetail.setUserName(rs.getString("userName"));
								userdetail.setPassword(rs.getString("password"));
								userdetail.setEmail_id((rs
										.getString("email_id")));
								userdetail.setLoginType((rs
										.getString("LoginType")));
								userdetail.setEid(rs.getInt("eid"));
								// TODO Auto-generated method stub
								return userdetail;
							}

						});
			} else {
				return null;
			}
		} catch (Exception ex) {
			System.out.println("NO USER FOUND !!!!");
			log.debug("NO USER FOUND : " + ex);
			return null;
		}
		return userdetail;
	}

	public void insertData(Object[] objects) {
		List<CustomerDetails> custDetailsList = (List<CustomerDetails>) objects[0];
		List<serviceDetails> servDetailsList = (List<serviceDetails>) objects[1];
		if (jdbctemp != null) {
			System.out.println("Customer Size : " + custDetailsList.size());
			System.out.println("Details Size : " + servDetailsList.size());

			try {
				if (custDetailsList.size() > 0) {
					for (CustomerDetails cd : custDetailsList) {
						try {
							System.out
									.println("inserting started ..... CUSTOMER DATA");
							jdbctemp.update(
									dao_constants.INSERT_DATA_CUSTOMER,
									new Object[] { cd.getCustomerId(),
											cd.getCustomerDetails(),
											cd.getChasisNumber(),
											cd.getEngineNumber(),
											cd.getDateOFsale(),
											cd.getInstalledDate() });
							jdbctemp.update(
									dao_constants.INSERT_SERVICE_ACTUAL,
									new Object[] { cd.getChasisNumber(),
											cd.getDateOFsale(),
											cd.getDateOFsale(),
											cd.getDateOFsale(),
											cd.getDateOFsale(),
											cd.getDateOFsale(),
											cd.getDateOFsale(),
											cd.getDateOFsale(),
											cd.getDateOFsale(),
											cd.getDateOFsale() });
							System.out.println("insert completed ....");
						} catch (org.springframework.dao.DuplicateKeyException ex) {
							System.out
									.println("Duplicate data Exception @ customer details insert data - :"
											+ cd.getChasisNumber());
							jdbctemp.update(
									dao_constants.UPDATE_INSTALLATION_DATE,
									new Object[] { cd.getInstalledDate(),
											cd.getChasisNumber() });
							System.out
									.println("updating the installation date for : "
											+ cd.getChasisNumber());
							log.debug("Duplicate data Exception @ customer details insert data -"
									+ ex);
						}

					}
				}
			} catch (Exception ex) {
				log.debug("Exception @ customer details insert data - :" + ex);
				System.out
						.println("Exception @ customer details insert data - :"
								+ ex);
			}

			try {
				System.out.println("inserting started ..... SERVICE DATA");

				if (servDetailsList.size() > 0) {
					for (serviceDetails sd : servDetailsList) {
						jdbctemp.update(
								dao_constants.INSERT_DATA_SERVICE,
								new Object[] { sd.getChasisNumber(),
										sd.getServiceName(),
										sd.getServiceDate(), sd.getJobcardNo(),
										sd.getHours(), sd.getCostCharged() });
					}
				}

			} catch (Exception ex) {
				log.debug("Exception @ service details insert data - :" + ex);

				System.out
						.println("Exception @ service details insert data - :"
								+ ex);
			}

		} else {
			System.out.println("JDBC IS NULL");
		}
	}

	public void insertSpareDetails(Object[] obj) {
		List<spares_details> sparesDetails = (List<spares_details>) obj[0];
		if (jdbctemp != null) {
			if (sparesDetails.size() > 0) {
				System.out.println("INSERT SPARES DETAILS STARTED..... SIZE :"
						+ sparesDetails.size());
				for (spares_details sd : sparesDetails) {
					if (sd != null) {
						try {
							jdbctemp.update(
									dao_constants.INSERT_SPARES_DETAILS,
									new Object[] { sd.getSpare_code(),
											sd.getPart_description(), "NO",
											sd.getCost(), sd.getMin_order() });
						} catch (org.springframework.dao.DuplicateKeyException ex) {
							log.debug("Duplicate Exception at insertSparesDetails : "
									+ ex.getMessage()
									+ " for :"
									+ sd.getSpare_code());
							System.out
									.println("Duplicate Exception at insertSparesDetails : "
											+ ex.getMessage()
											+ " for :"
											+ sd.getSpare_code());
						} catch (Exception ex) {
							log.debug("Exception insert spare parts : " + ex);
							System.out
									.println("Exception insert spare parts : "
											+ ex);
						}
					}
				}
				System.out
						.println("INSERT SPARES DETAILS COMPLETED.....SUCCESS");

			}
		} else {
			System.out.println("jdbctemp is NULL");
		}
	}

	public void insertServiceData(Object[] objects) {
		List<CustomerDetails> customerList = (List<CustomerDetails>) objects[0];
		List<serviceDetails> serviceList = (List<serviceDetails>) objects[1];
		System.out.println("customer size : " + customerList.size());
		System.out.println("Service list size : " + serviceList.size());

		try {
			if (customerList.size() > 0) {
				for (CustomerDetails cd : customerList) {
					jdbctemp.update(
							dao_constants.UPDATE_INSTALLATION_DATE,
							new Object[] { cd.getInstalledDate(),
									cd.getChasisNumber() });
				}
				log.info("CUSTOMER INSTALLATION DETAILS UPDATION COMPLETE ...... ");
				System.out
						.println("CUSTOMER INSTALLATION DETAILS UPDATION COMPLETE ...... ");
			}
		} catch (Exception ex) {
			log.debug("Exception @ customer updating customer installation data - :"
					+ ex);
			System.out
					.println("Exception @ customer updating customer installation data - :"
							+ ex);
		}

		try {
			if (serviceList.size() > 0) {
				for (serviceDetails sd : serviceList) {
					jdbctemp.update(
							dao_constants.INSERT_DATA_SERVICE,
							new Object[] { sd.getChasisNumber(),
									sd.getServiceName(), sd.getServiceDate(),
									sd.getJobcardNo(), sd.getHours(),
									sd.getCostCharged() });
				}
				log.info("SERVICE DETAILS INSERT COMPLETED.....");
				System.out.println("SERVICE DETAILS INSERT COMPLETED.....");
			}

		} catch (Exception ex) {
			log.debug("Exception @ service details insert data - :" + ex);

			System.out.println("Exception @ service details insert data - :"
					+ ex);
		}

	}

	public void insertCustomerData(List<CustomerDetails> listOfCustomers) {

		if (jdbctemp != null) {
			System.out.println("Customer Size From Insert Method : "
					+ listOfCustomers.size());

			try {
				if (listOfCustomers.size() > 0) {
					// jdbctemp.execute(dao_constants.DELETE_DATA_CUSTOMER);
					System.out
							.println("CUSTOMER TABLE IS CLEARED SUCCESSFULLY");
					for (CustomerDetails cd : listOfCustomers) {
						try {
							jdbctemp.update(
									dao_constants.INSERT_DATA_CUSTOMER,
									new Object[] { cd.getCustomerId(),
											cd.getCustomerDetails(),
											cd.getChasisNumber(),
											cd.getEngineNumber(),
											cd.getDateOFsale(),
											cd.getInstalledDate() });
							jdbctemp.update(
									dao_constants.INSERT_SERVICE_ACTUAL,
									new Object[] { cd.getChasisNumber(),
											cd.getDateOFsale(),
											cd.getDateOFsale(),
											cd.getDateOFsale(),
											cd.getDateOFsale(),
											cd.getDateOFsale(),
											cd.getDateOFsale(),
											cd.getDateOFsale(),
											cd.getDateOFsale(),
											cd.getDateOFsale() });
						} catch (org.springframework.dao.DuplicateKeyException ex) {
							log.debug("Duplicate data Exception @ customer details insert data - :"
									+ cd.getChasisNumber());
							System.out
									.println("Duplicate data Exception @ customer details insert data - :"
											+ cd.getChasisNumber());
						}

					}
					log.info("CUSTOMER TABLE IS UPDATED SUCCESSFULLY");
					System.out
							.println("CUSTOMER TABLE IS UPDATED SUCCESSFULLY");

				}
			}

			catch (Exception ex) {
				log.debug("Exception @ customer details insert data - :" + ex);
				System.out
						.println("Exception @ customer details insert data - :"
								+ ex);
			}

		} else {
			log.debug("JDBC IS NULL");
			System.out.println("JDBC IS NULL");
		}
	}

	public List<ServiceActual> getServiceList(String serviceCutoff) {
		return (List<ServiceActual>) jdbctemp.query(
				dao_constants.SELECT_ACTUAL_SERVICE_BY_MONTH, new Object[] {
						serviceCutoff, serviceCutoff, serviceCutoff,
						serviceCutoff, serviceCutoff, serviceCutoff,
						serviceCutoff, serviceCutoff, serviceCutoff,
						serviceCutoff, serviceCutoff, serviceCutoff,
						serviceCutoff, serviceCutoff, serviceCutoff,
						serviceCutoff }, new RowMapper<ServiceActual>() {

					@Override
					public ServiceActual mapRow(ResultSet rs, int arg1)
							throws SQLException {
						ServiceActual sa = new ServiceActual();
						sa.setChasisnumber(rs.getString(2));
						sa.setFirstServiceDate(rs.getDate(3));
						sa.setFirstServiceHours(rs.getInt(4));
						sa.setSecondServiceDate(rs.getDate(5));
						sa.setSecondServiceHours(rs.getInt(6));
						sa.setThirdServiceDate(rs.getDate(7));
						sa.setThirdServiceHours(rs.getInt(8));
						sa.setFourthServiceDate(rs.getDate(9));
						sa.setFourthServiceHours(rs.getInt(10));
						sa.setFifthServiceDate(rs.getDate(11));
						sa.setFifthServiceHours(rs.getInt(12));
						sa.setSixthServiceDate(rs.getDate(13));
						sa.setSixthServiceHours(rs.getInt(14));
						sa.setSeventhServiceDate(rs.getDate(15));
						sa.setSeventhServiceHours(rs.getInt(16));
						sa.setEigthServiceDate(rs.getDate(17));
						sa.setEigthServiceHours(rs.getInt(18));
						return sa;
					}
				});
	}

	public List<String> getChasisNoList(String serviceCutoff) {
		List<String> chasisList = null;
		try {
			chasisList = jdbctemp.queryForList(
					dao_constants.SELECT_FIRST_SERVICE_BYDATE, new Object[] {
							serviceCutoff, serviceCutoff }, String.class);
		} catch (Exception e) {
			log.debug("Exception at getChasisNoList : " + e);
			System.out.println("Exception at getChasisNoList : " + e);
		}
		return chasisList;
	}

	public Object getServiceDetailByName(String serviceName,
			final String chasisNumber) {
		Object chasisList = null;
		try {
			chasisList = jdbctemp.queryForObject(
					dao_constants.SELECT_SERVICEDETAILS_BYNAME, new Object[] {
							chasisNumber, serviceName },
					new RowMapper<serviceDetails>() {

						@Override
						public serviceDetails mapRow(ResultSet rs, int count)
								throws SQLException {
							serviceDetails sd = new serviceDetails();
							sd.setServiceName(rs.getString(1));
							sd.setJobcardNo(rs.getString(3));
							sd.setServiceDate(ft.format(rs.getDate(2)));
							sd.setHours(rs.getInt(4));
							sd.setChasisNumber(chasisNumber);
							return sd;
						}

					});
		} catch (org.springframework.dao.EmptyResultDataAccessException aa) {
			log.debug("Exception at getServiceDetailByName : " + aa);
			// System.out.println("DATA IS NULL !!!! FOR CURRENT DATA");
		} catch (Exception e) {
			log.debug("Exception at getServiceDetailByName : " + e);
			System.out.println("Exception at getServiceDetailByName : " + e);
		}
		return chasisList;
	}

	public String getCustomerDetails_byChasisNo(String chasisNumber) {
		String customerDetails = "";
		try {
			customerDetails = (String) jdbctemp.queryForObject(
					dao_constants.SELECT_CUSTOMERDETAILS_BY_CHASISNO,
					new Object[] { chasisNumber }, String.class);
		} catch (Exception e) {
			log.debug("Exception at getCustomerDetails_byChasisNo : " + e);

			System.out.println("Exception at getCustomerDetails_byChasisNo : "
					+ e);
		}
		return customerDetails;
	}

	public Object getCustomerDetails(String ChasisNo) {
		return (Object) jdbctemp.queryForObject(
				dao_constants.SELECT_CUSTOMER_BY_CHASISNO,
				new Object[] { ChasisNo }, new RowMapper<Object>() {

					@Override
					public CustomerDetails mapRow(ResultSet rs, int arg1)
							throws SQLException {
						CustomerDetails sa = new CustomerDetails();
						sa.setChasisNumber(rs.getString(3));
						sa.setCustomerDetails(rs.getString(2));
						sa.setCustomerId(rs.getInt(1));
						sa.setDateOFsale(ft.format(rs.getDate(5)));
						sa.setEngineNumber(rs.getString(4));
						sa.setInstalledDate(ft.format(rs.getDate(6)));
						return sa;
					}
				});
	}

	public List<serviceDetails> getServiceDetails(String ChasisNo) {
		return (List<serviceDetails>) jdbctemp.query(
				dao_constants.SELECT_SERVICE_BY_CHASISNO,
				new Object[] { ChasisNo }, new RowMapper<serviceDetails>() {

					@Override
					public serviceDetails mapRow(ResultSet rs, int arg1)
							throws SQLException {
						serviceDetails sa = new serviceDetails();
						sa.setChasisNumber(rs.getString(2));
						sa.setServiceName(rs.getString(3));
						sa.setServiceDate(ft.format(rs.getDate(4)));
						sa.setJobcardNo(rs.getString(5));
						sa.setHours(rs.getInt(6));
						sa.setCostCharged(new Integer(rs.getInt(7)).toString());
						return sa;
					}
				});
	}

	public List<serviceDetails> getServiceDetailsByName(String ChasisNo,
			String service) {
		return (List<serviceDetails>) jdbctemp.query(
				dao_constants.SELECT_SERVICE_BY_CHASISNO,
				new Object[] { ChasisNo }, new RowMapper<serviceDetails>() {

					@Override
					public serviceDetails mapRow(ResultSet rs, int arg1)
							throws SQLException {
						serviceDetails sa = new serviceDetails();
						sa.setChasisNumber(rs.getString(2));
						sa.setServiceName(rs.getString(3));
						sa.setServiceDate(ft.format(rs.getDate(4)));
						sa.setJobcardNo(rs.getString(5));
						sa.setHours(rs.getInt(6));
						sa.setCostCharged(new Integer(rs.getInt(7)).toString());
						return sa;
					}
				});
	}

	public Object getServiceDetail(String ChasisNo) {
		return (Object) jdbctemp.query(
				dao_constants.SELECT_SERVICE_BY_CHASISNO,
				new Object[] { ChasisNo }, new RowMapper<serviceDetails>() {

					@Override
					public serviceDetails mapRow(ResultSet rs, int arg1)
							throws SQLException {
						serviceDetails sa = new serviceDetails();
						sa.setChasisNumber(rs.getString(2));
						sa.setServiceName(rs.getString(3));
						sa.setServiceDate(ft.format(rs.getDate(4)));
						sa.setJobcardNo(rs.getString(5));
						sa.setHours(rs.getInt(6));
						sa.setCostCharged(new Integer(rs.getInt(7)).toString());
						return sa;
					}
				});
	}

	public List<PendingService> getFirstServicePendinglist(int fromMonth,
			int toMonth, int fromYear, int toYear) {
		return (List<PendingService>) jdbctemp.query(
				dao_constants.SELECT_SERVICE_PENDING_BYMONTH_FIRST,
				new Object[] { fromMonth, toMonth, fromYear, toYear, fromMonth,
						toMonth, fromYear, toYear },
				new RowMapper<PendingService>() {

					@Override
					public PendingService mapRow(ResultSet rs, int recCount)
							throws SQLException {
						// TODO Auto-generated method stub
						PendingService ps = new PendingService();
						ps.setChasisNumber(rs.getString(1));
						ps.setActual_date_of_service(rs.getDate(2));
						ps.setCustomerDetails(rs.getString(3));
						ps.setServiceName("1st Service");
						return ps;
					}
				});
	}

	public List<PendingService> getSecondServicePendinglist(int fromMonth,
			int toMonth, int fromYear, int toYear) {
		return (List<PendingService>) jdbctemp.query(
				dao_constants.SELECT_SERVICE_PENDING_BYMONTH_SECOND,
				new Object[] { fromMonth, toMonth, fromYear, toYear, fromMonth,
						toMonth, fromYear, toYear },
				new RowMapper<PendingService>() {

					@Override
					public PendingService mapRow(ResultSet rs, int recCount)
							throws SQLException {
						// TODO Auto-generated method stub
						PendingService ps = new PendingService();
						ps.setChasisNumber(rs.getString(1));
						ps.setActual_date_of_service(rs.getDate(2));
						ps.setCustomerDetails(rs.getString(3));
						ps.setServiceName("2nd Service");
						return ps;
					}
				});
	}

	public List<PendingService> getThirdServicePendinglist(int fromMonth,
			int toMonth, int fromYear, int toYear) {
		return (List<PendingService>) jdbctemp.query(
				dao_constants.SELECT_SERVICE_PENDING_BYMONTH_THIRD,
				new Object[] { fromMonth, toMonth, fromYear, toYear, fromMonth,
						toMonth, fromYear, toYear },
				new RowMapper<PendingService>() {

					@Override
					public PendingService mapRow(ResultSet rs, int recCount)
							throws SQLException {
						// TODO Auto-generated method stub
						PendingService ps = new PendingService();
						ps.setChasisNumber(rs.getString(1));
						ps.setActual_date_of_service(rs.getDate(2));
						ps.setCustomerDetails(rs.getString(3));
						ps.setServiceName("3rd Service");
						return ps;
					}
				});
	}

	public List<PendingService> getFourthServicePendinglist(int fromMonth,
			int toMonth, int fromYear, int toYear) {
		return (List<PendingService>) jdbctemp.query(
				dao_constants.SELECT_SERVICE_PENDING_BYMONTH_FOURTH,
				new Object[] { fromMonth, toMonth, fromYear, toYear, fromMonth,
						toMonth, fromYear, toYear },
				new RowMapper<PendingService>() {

					@Override
					public PendingService mapRow(ResultSet rs, int recCount)
							throws SQLException {
						// TODO Auto-generated method stub
						PendingService ps = new PendingService();
						ps.setChasisNumber(rs.getString(1));
						ps.setActual_date_of_service(rs.getDate(2));
						ps.setCustomerDetails(rs.getString(3));
						ps.setServiceName("4th Service");
						return ps;
					}
				});
	}

	public List<PendingService> getFifthServicePendinglist(int fromMonth,
			int toMonth, int fromYear, int toYear) {
		return (List<PendingService>) jdbctemp.query(
				dao_constants.SELECT_SERVICE_PENDING_BYMONTH_FIFTH,
				new Object[] { fromMonth, toMonth, fromYear, toYear, fromMonth,
						toMonth, fromYear, toYear },
				new RowMapper<PendingService>() {

					@Override
					public PendingService mapRow(ResultSet rs, int recCount)
							throws SQLException {
						// TODO Auto-generated method stub
						PendingService ps = new PendingService();
						ps.setChasisNumber(rs.getString(1));
						ps.setActual_date_of_service(rs.getDate(2));
						ps.setCustomerDetails(rs.getString(3));
						ps.setServiceName("1st Service");
						return ps;
					}
				});
	}

	public List<PendingService> getSixthServicePendinglist(int fromMonth,
			int toMonth, int fromYear, int toYear) {
		return (List<PendingService>) jdbctemp.query(
				dao_constants.SELECT_SERVICE_PENDING_BYMONTH_SIXTH,
				new Object[] { fromMonth, toMonth, fromYear, toYear, fromMonth,
						toMonth, fromYear, toYear },
				new RowMapper<PendingService>() {

					@Override
					public PendingService mapRow(ResultSet rs, int recCount)
							throws SQLException {
						// TODO Auto-generated method stub
						PendingService ps = new PendingService();
						ps.setChasisNumber(rs.getString(1));
						ps.setActual_date_of_service(rs.getDate(2));
						ps.setCustomerDetails(rs.getString(3));
						ps.setServiceName("2nd Service");
						return ps;
					}
				});
	}

	public List<PendingService> getSeventhServicePendinglist(int fromMonth,
			int toMonth, int fromYear, int toYear) {
		return (List<PendingService>) jdbctemp.query(
				dao_constants.SELECT_SERVICE_PENDING_BYMONTH_SEVENTH,
				new Object[] { fromMonth, toMonth, fromYear, toYear, fromMonth,
						toMonth, fromYear, toYear },
				new RowMapper<PendingService>() {

					@Override
					public PendingService mapRow(ResultSet rs, int recCount)
							throws SQLException {
						// TODO Auto-generated method stub
						PendingService ps = new PendingService();
						ps.setChasisNumber(rs.getString(1));
						ps.setActual_date_of_service(rs.getDate(2));
						ps.setCustomerDetails(rs.getString(3));
						ps.setServiceName("3rd Service");
						return ps;
					}
				});
	}

	public List<PendingService> getEigthServicePendinglist(int fromMonth,
			int toMonth, int fromYear, int toYear) {
		return (List<PendingService>) jdbctemp.query(
				dao_constants.SELECT_SERVICE_PENDING_BYMONTH_EIGTH,
				new Object[] { fromMonth, toMonth, fromYear, toYear, fromMonth,
						toMonth, fromYear, toYear },
				new RowMapper<PendingService>() {

					@Override
					public PendingService mapRow(ResultSet rs, int recCount)
							throws SQLException {
						// TODO Auto-generated method stub
						PendingService ps = new PendingService();
						ps.setChasisNumber(rs.getString(1));
						ps.setActual_date_of_service(rs.getDate(2));
						ps.setCustomerDetails(rs.getString(3));
						ps.setServiceName("4th Service");
						return ps;
					}
				});
	}

	/* METHOD TO GET THE DATA FOR CHART ... */
	public Object[] getDataForChart() {
		List<Map<String, Object>> firstService = jdbctemp
				.queryForList(dao_constants.GET_DURATION_COUNT_1ST_SERVICE);
		List<Map<String, Object>> secondService = jdbctemp
				.queryForList(dao_constants.GET_DURATION_COUNT_2ND_SERVICE);
		List<Map<String, Object>> thirdService = jdbctemp
				.queryForList(dao_constants.GET_DURATION_COUNT_3RD_SERVICE);
		List<Map<String, Object>> fourthService = jdbctemp
				.queryForList(dao_constants.GET_DURATION_COUNT_4TH_SERVICE);
		List<Map<String, Object>> fifthService = jdbctemp
				.queryForList(dao_constants.GET_DURATION_COUNT_5TH_SERVICE);
		List<Map<String, Object>> sixthService = jdbctemp
				.queryForList(dao_constants.GET_DURATION_COUNT_6TH_SERVICE);
		List<Map<String, Object>> seventhService = jdbctemp
				.queryForList(dao_constants.GET_DURATION_COUNT_7TH_SERVICE);
		List<Map<String, Object>> eigthService = jdbctemp
				.queryForList(dao_constants.GET_DURATION_COUNT_8TH_SERVICE);

		List<Map<String, Object>> completedService = jdbctemp
				.queryForList(dao_constants.GET_DURATION_COUNT_COMPLETED_SERVICE);
		System.out.println("%%%% : " + completedService.toString());
		LinkedHashMap<String, Integer> combinedCompletedServices = getCombined(completedService);

		List list = new ArrayList();
		list.add(firstService);
		list.add(secondService);
		list.add(thirdService);
		list.add(fourthService);
		list.add(fifthService);
		list.add(sixthService);
		list.add(seventhService);
		list.add(eigthService);

		List service1 = (List) list.get(0);
		List service2 = (List) list.get(1);
		List service3 = (List) list.get(2);
		List service4 = (List) list.get(3);
		List service5 = (List) list.get(4);
		List service6 = (List) list.get(5);
		List service7 = (List) list.get(6);
		List service8 = (List) list.get(7);

		LinkedHashMap combinedservice1 = getCombined(service1);
		LinkedHashMap combinedservice2 = getCombined(service2);
		LinkedHashMap combinedservice3 = getCombined(service3);
		LinkedHashMap combinedservice4 = getCombined(service4);
		LinkedHashMap combinedservice5 = getCombined(service5);
		LinkedHashMap combinedservice6 = getCombined(service6);
		LinkedHashMap combinedservice7 = getCombined(service7);
		LinkedHashMap combinedservice8 = getCombined(service8);

		List listOfServices = new ArrayList();

		listOfServices.add(combinedservice1);
		listOfServices.add(combinedservice2);
		listOfServices.add(combinedservice3);
		listOfServices.add(combinedservice4);
		listOfServices.add(combinedservice5);
		listOfServices.add(combinedservice6);
		listOfServices.add(combinedservice7);
		listOfServices.add(combinedservice8);

		// System.out.println("List : "+test.size());
		// Map mappie = test;

		LinkedHashMap<String, Integer> combinedPendingServices = getAddedMap(listOfServices);
		System.out.println("Pending Services : getDataForChart ::"
				+ (combinedPendingServices).toString());
		System.out.println("Completed Sevices : getDataForChart :"
				+ combinedCompletedServices.toString());
		Object[] objects=null;
		try {
			objects = new Object[] { sortData(combinedPendingServices),
					sortData(combinedCompletedServices) };
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return objects;

	}
	
	private LinkedHashMap<String, Integer> sortData(LinkedHashMap<String, Integer> datas) throws ParseException{
		
		Map<Date, Integer> m = new HashMap<Date, Integer>();
        DateFormat dateFormat = new SimpleDateFormat("MMM-yyyy");
        Set<String> keys = datas.keySet();
        for(String key:keys){
        	 m.put(new java.sql.Date(dateFormat.parse(key.substring(0, 3)+"-20"+key.substring(3,5)).getTime()),datas.get(key));
        }
       
        Map<Date, Integer> m1 = new TreeMap(m);
        Set<Date> keys1 =m1.keySet();
        LinkedHashMap<String, Integer> finalData = new LinkedHashMap<String, Integer>();

        DateFormat dfMonth = new SimpleDateFormat("MMM"); 
        DateFormat dfYear = new SimpleDateFormat("yyyy"); 
        for(Date date:keys1){
        	finalData.put(dfMonth.format(date)+""+dfYear.format(date).substring(2,4), m1.get(date));
        }
        return finalData;
        
	}

	private LinkedHashMap<String, Integer> getCombined(List getList) {
		LinkedHashMap<String, Integer> combineMap = new LinkedHashMap<String, Integer>();

		try {
			for (int i = 0; i < getList.size(); i++) {
				// System.out.println("getList.get(i).toString() : "+getList.get(i).toString());
				if (getList.get(i).toString().length() > 3)
					combineMap.put(getList.get(i).toString().substring(18, 23)
							.replace('}', ' ').trim(), Integer.parseInt(getList.get(i).toString().substring(37, 39).replace('}', ' ').trim()));
				// System.out.println("Key :"+getList.get(i).toString().substring(18,23).replace('}',' ').trim()+" Value : "+getList.get(i).toString().substring(37,39).replace('}',' ').trim());
				// System.out.println("i : "+i);
			}
		} catch (Exception e) {
			System.out.println("Exception : st getCombine " + e);
		}
		System.out.println("** : " + combineMap.toString());
		return combineMap;
	}

	private LinkedHashMap<String, Integer> getAddedMap(List list) {
		// To get the maximum size of the map
		int count = 0;
		int listposition = 0;
		try {

			for (int i = 0; i < list.size(); i++) {
				System.out.println("Size : Of " + i + " is : "
						+ ((LinkedHashMap) list.get(i)).size());
				// if((i+1) < list.size())
				{
					if (count < ((LinkedHashMap) list.get(i)).size()) {
						count = ((LinkedHashMap) list.get(i)).size();
						listposition = i;
					}
				}
			}
			log.debug("Max Size : " + count);
			log.debug("Max Size  position : " + listposition);

			log.debug("Max list size is : "
					+ ((LinkedHashMap) list.get(listposition)).toString());
		} catch (Exception e) {
			log.debug("exception in getting the Max size : " + e.getMessage());
		}

		try {
			Set keyset = ((LinkedHashMap) list.get(listposition)).keySet();
			String[] keysetArray = new String[((LinkedHashMap) list
					.get(listposition)).size()];
			Iterator ite = keyset.iterator();
			int x = 0;
			while (ite.hasNext()) {

				keysetArray[x] = ite.next().toString();
				x++;
			}
			for (int i = 0; i < list.size(); i++) {
				if (i != listposition) {
					for (int j = 0; j < keysetArray.length; j++) {
						if (((LinkedHashMap) list.get(i))
								.containsKey(keysetArray[j])) {
							if (((LinkedHashMap) list.get(listposition))
									.get(keysetArray[j]).toString().length() > 0
									&& ((LinkedHashMap) list.get(i))
											.get(keysetArray[j]).toString()
											.length() > 0) {
								int tempcount = Integer
										.parseInt((String) ((LinkedHashMap) list
												.get(listposition))
												.get(keysetArray[j]).toString()
												.trim());
								int tempcount1 = Integer
										.parseInt((String) ((LinkedHashMap) list
												.get(i)).get(keysetArray[j])
												.toString().trim());
								((LinkedHashMap) list.get(listposition)).put(
										keysetArray[j], tempcount + tempcount1);
							}
						}
					}
				}

			}
		} catch (Exception ex) {
			log.debug("Exception in adding data " + ex.getMessage());
		}
		System.out.println("TOTAL CONTENT : "
				+ ((LinkedHashMap) list.get(listposition)).toString());

		return ((LinkedHashMap) list.get(listposition));
	}

	public List<PendingService> getPendingFirstServiceForSeletedMonth(
			ReportFilter reportFilter) {
		log.debug("ReportFilter.ExportFilterField : "
				+ reportFilter.getExportTypeFilter());
		Object param[] = getQueryParam(reportFilter);
		String query = null;
		log.info("Param:     " + param[0]);
		log.debug("Param Array:     " + param);
		if (ReportConstants.DURATION_REPORT.equals(reportFilter.getFrom())
				&& !ReportConstants.NAVIGATE_LINK.equals(reportFilter
						.getNavLink())) {
			query = dao_constants.SELECT_SERVICE_PENDING_BYMONTH_FIRST;
		} else {
			query = dao_constants.SELECT_SERVICE_PENDING_BYSELECTED_MONTH_FIRST;
		}
		log.info("QUERY:" + query);
		return (List<PendingService>) jdbctemp.query(query, param,
				new RowMapper<PendingService>() {

					@Override
					public PendingService mapRow(ResultSet rs, int recCount)
							throws SQLException {
						// TODO Auto-generated method stub
						PendingService ps = new PendingService();
						ps.setChasisNumber(rs.getString(1));
						ps.setActual_date_of_service(rs.getDate(2));
						ps.setCustomerDetails(rs.getString(3));
						ps.setServiceName(rs.getString(4));
						return ps;
					}
				});
	}

	public List<PendingService> getPendingSecondServiceForSeletedMonth(
			ReportFilter reportFilter) {
		Object param[] = getQueryParam(reportFilter);
		String query = null;
		if (ReportConstants.DURATION_REPORT.equals(reportFilter.getFrom())
				&& !ReportConstants.NAVIGATE_LINK.equals(reportFilter
						.getNavLink())) {
			query = dao_constants.SELECT_SERVICE_PENDING_BYMONTH_SECOND;
		} else {
			query = dao_constants.SELECT_SERVICE_PENDING_BYSELECTED_MONTH_SECOND;
		}
		return (List<PendingService>) jdbctemp.query(query, param,
				new RowMapper<PendingService>() {

					@Override
					public PendingService mapRow(ResultSet rs, int recCount)
							throws SQLException {
						// TODO Auto-generated method stub
						PendingService ps = new PendingService();
						ps.setChasisNumber(rs.getString(1));
						ps.setActual_date_of_service(rs.getDate(2));
						ps.setCustomerDetails(rs.getString(3));
						ps.setServiceName(rs.getString(4));
						return ps;
					}
				});
	}

	public List<PendingService> getPendingThirdServiceForSeletedMonth(
			ReportFilter reportFilter) {
		Object param[] = getQueryParam(reportFilter);
		String query = null;
		if (ReportConstants.DURATION_REPORT.equals(reportFilter.getFrom())
				&& !ReportConstants.NAVIGATE_LINK.equals(reportFilter
						.getNavLink())) {
			query = dao_constants.SELECT_SERVICE_PENDING_BYMONTH_THIRD;
		} else {
			query = dao_constants.SELECT_SERVICE_PENDING_BYSELECTED_MONTH_THIRD;
		}
		return (List<PendingService>) jdbctemp.query(query, param,
				new RowMapper<PendingService>() {

					@Override
					public PendingService mapRow(ResultSet rs, int recCount)
							throws SQLException {
						// TODO Auto-generated method stub
						PendingService ps = new PendingService();
						ps.setChasisNumber(rs.getString(1));
						ps.setActual_date_of_service(rs.getDate(2));
						ps.setCustomerDetails(rs.getString(3));
						ps.setServiceName(rs.getString(4));
						return ps;
					}
				});
	}

	public List<PendingService> getPendingFourthServiceForSeletedMonth(
			ReportFilter reportFilter) {
		Object param[] = getQueryParam(reportFilter);
		String query = null;
		if (ReportConstants.DURATION_REPORT.equals(reportFilter.getFrom())
				&& !ReportConstants.NAVIGATE_LINK.equals(reportFilter
						.getNavLink())) {
			query = dao_constants.SELECT_SERVICE_PENDING_BYMONTH_FOURTH;
		} else {
			query = dao_constants.SELECT_SERVICE_PENDING_BYSELECTED_MONTH_FOURTH;
		}
		return (List<PendingService>) jdbctemp.query(query, param,
				new RowMapper<PendingService>() {

					@Override
					public PendingService mapRow(ResultSet rs, int recCount)
							throws SQLException {
						// TODO Auto-generated method stub
						PendingService ps = new PendingService();
						ps.setChasisNumber(rs.getString(1));
						ps.setActual_date_of_service(rs.getDate(2));
						ps.setCustomerDetails(rs.getString(3));
						ps.setServiceName(rs.getString(4));
						return ps;
					}
				});
	}

	public List<PendingService> getPendingFifthServiceForSeletedMonth(
			ReportFilter reportFilter) {
		Object param[] = getQueryParam(reportFilter);
		String query = null;
		if (ReportConstants.DURATION_REPORT.equals(reportFilter.getFrom())
				&& !ReportConstants.NAVIGATE_LINK.equals(reportFilter
						.getNavLink())) {
			query = dao_constants.SELECT_SERVICE_PENDING_BYMONTH_FIFTH;
		} else {
			query = dao_constants.SELECT_SERVICE_PENDING_BYSELECTED_MONTH_FIFTH;
		}
		return (List<PendingService>) jdbctemp.query(query, param,
				new RowMapper<PendingService>() {

					@Override
					public PendingService mapRow(ResultSet rs, int recCount)
							throws SQLException {
						// TODO Auto-generated method stub
						PendingService ps = new PendingService();
						ps.setChasisNumber(rs.getString(1));
						ps.setActual_date_of_service(rs.getDate(2));
						ps.setCustomerDetails(rs.getString(3));
						ps.setServiceName(rs.getString(4));
						return ps;
					}
				});
	}
	
	

	public List<PendingService> getPendingSixthServiceForSeletedMonth(
			ReportFilter reportFilter) {
		Object param[] = getQueryParam(reportFilter);
		String query = null;
		if (ReportConstants.DURATION_REPORT.equals(reportFilter.getFrom())
				&& !ReportConstants.NAVIGATE_LINK.equals(reportFilter
						.getNavLink())) {
			query = dao_constants.SELECT_SERVICE_PENDING_BYMONTH_SIXTH;
		} else {
			query = dao_constants.SELECT_SERVICE_PENDING_BYSELECTED_MONTH_SIXTH;
		}
		return (List<PendingService>) jdbctemp.query(query, param,
				new RowMapper<PendingService>() {

					@Override
					public PendingService mapRow(ResultSet rs, int recCount)
							throws SQLException {
						// TODO Auto-generated method stub
						PendingService ps = new PendingService();
						ps.setChasisNumber(rs.getString(1));
						ps.setActual_date_of_service(rs.getDate(2));
						ps.setCustomerDetails(rs.getString(3));
						ps.setServiceName(rs.getString(4));
						return ps;
					}
				});
	}

	public List<PendingService> getPendingSeventhServiceForSeletedMonth(
			ReportFilter reportFilter) {
		Object param[] = getQueryParam(reportFilter);
		String query = null;
		if (ReportConstants.DURATION_REPORT.equals(reportFilter.getFrom())
				&& !ReportConstants.NAVIGATE_LINK.equals(reportFilter
						.getNavLink())) {
			query = dao_constants.SELECT_SERVICE_PENDING_BYMONTH_SEVENTH;
		} else {
			query = dao_constants.SELECT_SERVICE_PENDING_BYSELECTED_MONTH_SEVENTH;
		}
		return (List<PendingService>) jdbctemp.query(query, param,
				new RowMapper<PendingService>() {

					@Override
					public PendingService mapRow(ResultSet rs, int recCount)
							throws SQLException {
						// TODO Auto-generated method stub
						PendingService ps = new PendingService();
						ps.setChasisNumber(rs.getString(1));
						ps.setActual_date_of_service(rs.getDate(2));
						ps.setCustomerDetails(rs.getString(3));
						ps.setServiceName(rs.getString(4));
						return ps;
					}
				});
	}

	public List<PendingService> getPendingEigthServiceForSeletedMonth(
			ReportFilter reportFilter) {
		Object param[] = getQueryParam(reportFilter);
		String query = null;
		if (ReportConstants.DURATION_REPORT.equals(reportFilter.getFrom())
				&& !ReportConstants.NAVIGATE_LINK.equals(reportFilter
						.getNavLink())) {
			query = dao_constants.SELECT_SERVICE_PENDING_BYMONTH_EIGTH;
		} else {
			query = dao_constants.SELECT_SERVICE_PENDING_BYSELECTED_MONTH_EIGTH;
		}
		return (List<PendingService>) jdbctemp.query(query, param,
				new RowMapper<PendingService>() {

					@Override
					public PendingService mapRow(ResultSet rs, int recCount)
							throws SQLException {
						// TODO Auto-generated method stub
						PendingService ps = new PendingService();
						ps.setChasisNumber(rs.getString(1));
						ps.setActual_date_of_service(rs.getDate(2));
						ps.setCustomerDetails(rs.getString(3));
						ps.setServiceName(rs.getString(4));
						return ps;
					}
				});
	}

	/*
	 * This method if for getting the report for displaying and export the
	 * report with default Filter.
	 */

	public List<PendingService> getPendingServicesBySelectedMonth(
			ReportFilter reportFilter) {
		log.debug("entering  getPendingServicesBySelectedMonth !!!");
		List<PendingService> CompletePendingService = new ArrayList<PendingService>();

		// ListUtils.union(list1,list2);
		List<PendingService> PendingFirstService = getPendingFirstServiceForSeletedMonth(reportFilter);
		List<PendingService> PendingSecondService = getPendingSecondServiceForSeletedMonth(reportFilter);
		List<PendingService> PendingThirdService = getPendingThirdServiceForSeletedMonth(reportFilter);
		List<PendingService> PendingFourthService = getPendingFourthServiceForSeletedMonth(reportFilter);
		List<PendingService> PendingFifthService = getPendingFifthServiceForSeletedMonth(reportFilter);
		List<PendingService> PendingSixthService = getPendingSixthServiceForSeletedMonth(reportFilter);
		List<PendingService> PendingSeventhService = getPendingSeventhServiceForSeletedMonth(reportFilter);
		List<PendingService> PendingEigthService = getPendingEigthServiceForSeletedMonth(reportFilter);
		try {
			CompletePendingService.addAll(PendingFirstService);
			CompletePendingService.addAll(PendingSecondService);
			CompletePendingService.addAll(PendingThirdService);
			CompletePendingService.addAll(PendingFourthService);
			CompletePendingService.addAll(PendingFifthService);
			CompletePendingService.addAll(PendingSixthService);
			CompletePendingService.addAll(PendingSeventhService);
			CompletePendingService.addAll(PendingEigthService);

			Collections.sort(CompletePendingService, new Comparator() {
				@Override
				public int compare(Object obj1, Object obj2) {
					PendingService pendingServiceA = (PendingService) obj1;
					PendingService pendingServiceB = (PendingService) obj2;
					return pendingServiceA.getCustomerDetails()
							.compareToIgnoreCase(
									pendingServiceA.getCustomerDetails());
				}

				@Override
				public Comparator reversed() {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public Comparator thenComparing(Comparator other) {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public Comparator thenComparing(Function keyExtractor,
						Comparator keyComparator) {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public Comparator thenComparing(Function keyExtractor) {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public Comparator thenComparingInt(ToIntFunction keyExtractor) {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public Comparator thenComparingLong(ToLongFunction keyExtractor) {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public Comparator thenComparingDouble(
						ToDoubleFunction keyExtractor) {
					// TODO Auto-generated method stub
					return null;
				} 

			});

			log.debug("Exiting  getPendingServicesBySelectedMonth !!! : size of list :"
					+ CompletePendingService.size());
		} catch (Exception ex) {
			log.debug("Exception while Adding the complete Pending Report :"
					+ ex);
		}
		return CompletePendingService;
	}

	/**
	 * Get query parameter based on report selection.
	 * 
	 * @param reportFilter
	 *            -
	 * @return parameter of the query.
	 */
	/*
	 * public Object[] getQueryParam(ReportFilter reportFilter) { int _Month =
	 * 0, _Year = 0, _Month1 = 0, _Year1 = 0; // Object param[] = null; if
	 * (ReportConstants.DURATION_REPORT.equals(reportFilter.getFrom()) &&
	 * !ReportConstants.NAVIGATE_LINK.equals(reportFilter .getNavLink()) &&
	 * reportUtil.isNotNullNotEmpty(reportFilter.getToDate())) { _Month =
	 * Integer.parseInt(simpleDateformatMonth
	 * .format(java.sql.Date.valueOf((reportFilter.getFromDate()
	 * .toString())))); _Year =
	 * Integer.parseInt(simpleDateformatYear.format(java.sql.Date
	 * .valueOf((reportFilter.getFromDate().toString())))); _Month1 =
	 * Integer.parseInt(simpleDateformatMonth
	 * .format(java.sql.Date.valueOf((reportFilter.getToDate() .toString()))));
	 * _Year1 = Integer.parseInt(simpleDateformatYear.format(java.sql.Date
	 * .valueOf((reportFilter.getToDate().toString()))));
	 * 
	 * if (reportFilter.getExportTypeFilter() == ExportFilterValue.TALUK) {
	 * Object param[] = new Object[] { _Month, _Month1, _Year, _Year1, _Month,
	 * _Month1, _Year, _Year1, reportFilter.getTalukName() };
	 * System.out.println("-------Param---" + param[0]); log.info("param value:"
	 * + param[0]); log.debug("Param----" + param[0]);
	 * 
	 * return param; } else if (reportFilter.getExportTypeFilter() ==
	 * ExportFilterValue.DISTRICT) { Object param[] = new Object[] { _Month,
	 * _Month1, _Year, _Year1, _Month, _Month1, _Year, _Year1,
	 * reportFilter.getDistrictName() }; System.out.println("-------Param---" +
	 * param[0]); log.info("param value:" + param[0]); log.debug("Param----" +
	 * param[0]);
	 * 
	 * return param; } else { Object param[] = new Object[] { _Month, _Month1,
	 * _Year, _Year1, _Month, _Month1, _Year, _Year1 };
	 * System.out.println("-------Param---" + param[0]); log.info("param value:"
	 * + param[0]); log.debug("Param----" + param[0]);
	 * 
	 * return param; }
	 * 
	 * } else if (ReportConstants.MONTHLY_REPORT .equals(reportFilter.getFrom())
	 * && !ReportConstants.NAVIGATE_LINK.equals(reportFilter .getNavLink()) &&
	 * reportUtil.isNotNullNotEmpty(reportFilter.getToDate())) { _Month =
	 * Integer.parseInt(simpleDateformatMonth
	 * .format(java.sql.Date.valueOf((reportFilter.getToDate() .toString()))));
	 * _Year = Integer.parseInt(simpleDateformatYear.format(java.sql.Date
	 * .valueOf((reportFilter.getToDate().toString())))); if
	 * (reportFilter.getExportTypeFilter() == ExportFilterValue.TALUK) { Object
	 * param[] = new Object[] { _Month, _Month1, _Year, _Year1, _Month, _Month1,
	 * _Year, _Year1, reportFilter.getTalukName() };
	 * System.out.println("-------Param---" + param[0]); log.info("param value:"
	 * + param[0]); log.debug("Param----" + param[0]);
	 * 
	 * return param; } else if (reportFilter.getExportTypeFilter() ==
	 * ExportFilterValue.DISTRICT) { Object param[] = new Object[] { _Month,
	 * _Month1, _Year, _Year1, _Month, _Month1, _Year, _Year1,
	 * reportFilter.getDistrictName() }; System.out.println("-------Param---" +
	 * param[0]); log.info("param value:" + param[0]); log.debug("Param----" +
	 * param[0]);
	 * 
	 * return param; } else { Object param[] = new Object[] { _Month, _Month1,
	 * _Year, _Year1, _Month, _Month1, _Year, _Year1 };
	 * System.out.println("-------Param---" + param[0]); log.info("param value:"
	 * + param[0]); log.debug("Param----" + param[0]);
	 * 
	 * return param; } } else { if (!reportUtil
	 * .isNotNullNotEmpty(reportFilter.getForCurrentMonth())) { Calendar
	 * currentDate = Calendar.getInstance(); SimpleDateFormat formatter = new
	 * SimpleDateFormat("yyyy-MM-dd"); String currentdate =
	 * formatter.format(currentDate.getTime());
	 * reportFilter.setForCurrentMonth(currentdate);
	 * reportFilter.setNavLink(ReportConstants.NAVIGATE_LINK); }
	 * 
	 * _Month = Integer.parseInt(simpleDateformatMonth
	 * .format(java.sql.Date.valueOf((reportFilter
	 * .getForCurrentMonth().toString())))); _Year =
	 * Integer.parseInt(simpleDateformatYear.format(java.sql.Date
	 * .valueOf((reportFilter.getForCurrentMonth().toString())))); if
	 * (reportFilter.getExportTypeFilter() == ExportFilterValue.TALUK) { Object
	 * param[] = new Object[] { _Month, _Month1, _Year, _Year1, _Month, _Month1,
	 * _Year, _Year1, reportFilter.getTalukName() };
	 * System.out.println("-------Param---" + param[0]); log.info("param value:"
	 * + param[0]); log.debug("Param----" + param[0]);
	 * 
	 * return param; } else if (reportFilter.getExportTypeFilter() ==
	 * ExportFilterValue.DISTRICT) { Object param[] = new Object[] { _Month,
	 * _Month1, _Year, _Year1, _Month, _Month1, _Year, _Year1,
	 * reportFilter.getDistrictName() }; System.out.println("-------Param---" +
	 * param[0]); log.info("param value:" + param[0]); log.debug("Param----" +
	 * param[0]); return param; } else { Object param[] = new Object[] { _Month,
	 * _Month1, _Year, _Year1, _Month, _Month1, _Year, _Year1 };
	 * System.out.println("-------Param---" + param[0]); log.info("param value:"
	 * + param[0]); log.debug("Param----" + param[0]); return param; } }
	 * 
	 * }
	 */

	/**
	 * Get query parameter based on report selection.
	 * 
	 * @param reportFilter
	 *            -
	 * @return parameter of the query.
	 */
	public Object[] getQueryParam(ReportFilter reportFilter) {
		int _Month = 0, _Year = 0, _Month1 = 0, _Year1 = 0;
		// Object param[] = null; For a Specific duaration
		if (ReportConstants.DURATION_REPORT.equals(reportFilter.getFrom())
				&& !ReportConstants.NAVIGATE_LINK.equals(reportFilter
						.getNavLink())
				&& reportUtil.isNotNullNotEmpty(reportFilter.getToDate())) {
			_Month = Integer.parseInt(simpleDateformatMonth
					.format(java.sql.Date.valueOf((reportFilter.getFromDate()
							.toString()))));
			_Year = Integer.parseInt(simpleDateformatYear.format(java.sql.Date
					.valueOf((reportFilter.getFromDate().toString()))));
			_Month1 = Integer.parseInt(simpleDateformatMonth
					.format(java.sql.Date.valueOf((reportFilter.getToDate()
							.toString()))));
			_Year1 = Integer.parseInt(simpleDateformatYear.format(java.sql.Date
					.valueOf((reportFilter.getToDate().toString()))));

			if (reportFilter.getExportTypeFilter() == ExportFilterValue.TALUK) {
				Object param[] = new Object[] { _Month, _Month1, _Year, _Year1,
						_Month, _Month1, _Year, _Year1,
						reportFilter.getTalukName() };
				return param;

			} else if (reportFilter.getExportTypeFilter() == ExportFilterValue.DISTRICT) {
				Object param[] = new Object[] { _Month, _Month1, _Year, _Year1,
						_Month, _Month1, _Year, _Year1,
						reportFilter.getDistrictName() };
				return param;
			} else {
				Object param[] = new Object[] { _Month, _Month1, _Year, _Year1,
						_Month, _Month1, _Year, _Year1 };
				return param;
			}
		} else if (ReportConstants.MONTHLY_REPORT // Monthly report.... with
													// just one date.
				.equals(reportFilter.getFrom())
				&& !ReportConstants.NAVIGATE_LINK.equals(reportFilter
						.getNavLink())
				&& reportUtil.isNotNullNotEmpty(reportFilter.getToDate())) {
			_Month = Integer.parseInt(simpleDateformatMonth
					.format(java.sql.Date.valueOf((reportFilter.getToDate()
							.toString()))));
			_Year = Integer.parseInt(simpleDateformatYear.format(java.sql.Date
					.valueOf((reportFilter.getToDate().toString()))));

			if (reportFilter.getExportTypeFilter() == ExportFilterValue.TALUK) {
				Object param[] = new Object[] { _Month, _Year, _Month, _Year,
						reportFilter.getTalukName() };
				return param;
			} else if (reportFilter.getExportTypeFilter() == ExportFilterValue.DISTRICT) {
				Object param[] = new Object[] { _Month, _Year, _Month, _Year,
						reportFilter.getDistrictName() };
				return param;
			} else {
				Object param[] = new Object[] { _Month, _Year, _Month, _Year };
				return param;
			}

		} else { // Report for current month - Default
			if (!reportUtil
					.isNotNullNotEmpty(reportFilter.getForCurrentMonth())) {
				Calendar currentDate = Calendar.getInstance();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				String currentdate = formatter.format(currentDate.getTime());
				reportFilter.setForCurrentMonth(currentdate);
				reportFilter.setNavLink(ReportConstants.NAVIGATE_LINK);
			}

			_Month = Integer.parseInt(simpleDateformatMonth
					.format(java.sql.Date.valueOf((reportFilter
							.getForCurrentMonth().toString()))));
			_Year = Integer.parseInt(simpleDateformatYear.format(java.sql.Date
					.valueOf((reportFilter.getForCurrentMonth().toString()))));

			if (reportFilter.getExportTypeFilter() == ExportFilterValue.TALUK) {
				Object param[] = new Object[] { _Month, _Year, _Month, _Year,
						reportFilter.getTalukName() };
				return param;

			} else if (reportFilter.getExportTypeFilter() == ExportFilterValue.DISTRICT) {
				Object param[] = new Object[] { _Month, _Year, _Month, _Year,
						reportFilter.getDistrictName() };
				return param;
			} else {
				Object param[] = new Object[] { _Month, _Year, _Month, _Year };
				return param;
			}

		}

	}

	/**
	 * Get the query based on the report selection.
	 * 
	 * @param reportFilter
	 *            - Holds the duration details and etc.,
	 * @param service
	 *            - name of the service. eg:FIRST, SECOND.,
	 * @return query for the report.
	 */
	public String getQueryForService(ReportFilter reportFilter, String service) {
		StringBuilder queryForm = new StringBuilder();
		String query = null;
		if (ReportConstants.DURATION_REPORT.equals(reportFilter.getFrom())
				&& !ReportConstants.NAVIGATE_LINK.equals(reportFilter
						.getNavLink())) {
			queryForm.append("SELECT_SERVICE_PENDING_BYMONTH_SERVICE_");
			queryForm.append(service);
			query = queryForm.toString();
		} else {
			queryForm.append("SELECT_SERVICE_PENDING_BYSELECTED_MONTH_");
			queryForm.append(service);
			query = queryForm.toString();
		}
		return query;
	}

	/**
	 * Gets the List of spare cost available.
	 * 
	 * @return List of Spare cost & code.
	 */
	@Override
	public List<SoldSpares> getSparesDetails() {
		return jdbctemp.query(dao_constants.GET_SPARECODE_COST,
				new SpareCostMapper());
	}
	@Override
	public List<SparesCountMetrics> getSparesSalesMetricsDetails(int noOfMonths) {
		
		return jdbctemp.query(dao_constants.SELECT_MTS_TOP_SPARES_DATA,new Object[]{noOfMonths},
				new SparesSalesMetricsDetailsMapper());
	}
	
	/**
	 * Row mapper implementation for Mapping result set to SpareCost.
	 */
	static class SparesSalesMetricsDetailsMapper implements RowMapper<SparesCountMetrics> {

		@Override
		public SparesCountMetrics mapRow(ResultSet resultset, int count)
				throws SQLException {
			SparesCountMetrics scm = new SparesCountMetrics();
			scm.setNoOfItemsSold(resultset.getInt("noOfSoldItems"));
			scm.setSoldDate(resultset.getString("solddate"));
			scm.setSparesCode(resultset.getString("spare_code").trim());
			scm.setSparesDesc(resultset.getString("part_description").trim());
			return scm;
		}
		
	}
	
	@Override
	public List<SparesSaleBySE> getSparesSalesMetricsBySE(int noOfMonths) {
		
		return jdbctemp.query(dao_constants.SELECT_SE_SPARES_SALE_VALUE,new Object[]{noOfMonths},
				new SparesSalesMetricsBySEMapper());
	}
	
	/**
	 * Row mapper implementation for Mapping result set to SpareCost.
	 */
	static class SparesSalesMetricsBySEMapper implements RowMapper<SparesSaleBySE> {

		@Override
		public SparesSaleBySE mapRow(ResultSet resultset, int count)
				throws SQLException {
			SparesSaleBySE scm = new SparesSaleBySE();
			scm.setEmployeeId(resultset.getString("EID"));
			scm.setEmployeeName(resultset.getString("Employee_Name"));
			scm.setJobcardNumber(resultset.getString("jobcardnumber").trim());
			scm.setTotalBillAmt(resultset.getDouble("TOTALBILLAMT"));
			return scm;
		}
		
	}

	/**
	 * Row mapper implementation for Mapping result set to SpareCost.
	 */
	static class SpareCostMapper implements RowMapper<SoldSpares> {

		/**
		 * Extract the values for all columns in the current row and maps to
		 * Spare Cost.
		 * 
		 * @param resultset
		 *            - A ResultSet to map.
		 * @param rowNum
		 *            - The number of the current row.
		 * @throws SQLException
		 *             - If a SQLException is encountered getting column values.
		 * @return Research area.
		 */
		@Override
		public SoldSpares mapRow(ResultSet resultset, int rowNum)
				throws SQLException {
			SoldSpares spareDetail = new SoldSpares();

			spareDetail.setSpareCode(resultset.getString("spare_code").trim());
			spareDetail.setCost(resultset.getFloat("cost"));
			return spareDetail;
		}
	}

	/**
	 * Add the details of job card
	 * 
	 * @param jobcardDetails
	 *            - like jobcard number, chasisnumber. etc.,
	 * @return message.
	 */
	@Override
	public String addJobcardDetail(JobCardDetails jobcardDetails) {
		log.info("Entered AddJobcard Detial DAO");
		int row = 0; 
		String message = "Failure";
		log.debug("Closure DATE : " + jobcardDetails.getClosureDate());
		List<SoldSpares> soldDetails = (List<SoldSpares>) jobcardDetails
				.getSpareDetails();
		log.info("SOLD SPARE DETAIL: " + soldDetails);
		if (jdbctemp != null) {
			try { 
				row = jdbctemp.update(
						dao_constants.ADD_JOBCARD_DETAIL,
						new Object[] { jobcardDetails.getBranch(),
								jobcardDetails.getDateOfComplaint(),
								jobcardDetails.getChasisNumber(),
								jobcardDetails.getNatureOfComplaint(),
								jobcardDetails.getActionTaken(),
								jobcardDetails.getHours(),
								jobcardDetails.getClosureDate(),
								jobcardDetails.getJobcardNumber(),
								jobcardDetails.getLabourAmount(),
								jobcardDetails.getTotalSpareCost(),
								jobcardDetails.getServiceComplaint(),
								jobcardDetails.getOverallCost(),
								jobcardDetails.getReceiptNumber(),
								jobcardDetails.getServiceEngineerCode(),
								jobcardDetails.getServiceType() ,
								jobcardDetails.getClosureDate(),
								jobcardDetails.getDateOfComplaint()
						});
				log.debug("Rows Updated in Jobcard Table: " + row);
				row = jdbctemp.update(
						dao_constants.INSERT_DATA_SERVICE,
						new Object[] { jobcardDetails.getChasisNumber(),
								jobcardDetails.getServiceType(),
								jobcardDetails.getClosureDate(),
								jobcardDetails.getJobcardNumber(),
								jobcardDetails.getHours(),
								jobcardDetails.getOverallCost() });
				log.debug("Rows Updated in Service details Table: " + row);

			} catch (org.springframework.dao.DuplicateKeyException ex) {
				log.debug("Duplicate Exception at insertSparesDetails : "
						+ ex.getMessage() + " for :"
						+ jobcardDetails.getJobcardNumber());
				System.out
						.println("Duplicate Exception at insertSparesDetails : "
								+ ex.getMessage()
								+ " for :"
								+ jobcardDetails.getJobcardNumber());
				return message;
			} catch (Exception ex) {
				log.debug("Exception insert jobcard details : " + ex);
				System.out.println("Exception insert jobcard details : " + ex);
			}

			if (row > 0 && soldDetails.size() > 0) {
				message = "success";
				log.info("INSERT SOLD SPARES DETAILS STARTED..... SIZE :"
						+ soldDetails.size());
				for (SoldSpares sd : soldDetails) {
					if (sd != null) {
						try {
							if (sd.getTotal() > 0) {
								jdbctemp.update(
										dao_constants.ADD_SOLDSPARE_DETAIL,
										new Object[] {
												jobcardDetails
														.getJobcardNumber(),
												sd.getSpareCode().trim(),
												sd.getCost(),
												sd.getQuantity(),
												sd.getTotal(),
												jobcardDetails
														.getReceiptNumber() });
							}
						} catch (Exception ex) {
							log.debug("Exception insert spare parts : " + ex);
							System.out
									.println("Exception insert spare parts : "
											+ ex);
						}
					}
				}
				log.info("INSERT SPARES DETAILS COMPLETED.....SUCCESS");

			}
		} else {
			log.debug("jdbctemp is NULL");
		}

		log.info("Existing AddJobcard Detial DAO");
		return message;
	}

	/**
	 * Get the details for particular jobcard number.
	 * 
	 * @param jobcardDetail
	 *            - jobcardnumber.
	 */
	@Override
	public String getReportForParticularJobcard(
			final JobCardDetails jobcardDetails) {
		log.info("Entered get repor for particular jobcard DAO");
		String message = "Failure";
		if (jdbctemp != null) {
			try {
				log.info("QUERY ::: "
						+ dao_constants.GETDETAIL_FOR_PARTICULAR_JOBCARD);
				log.info("QUERY 1 ::: "
						+ dao_constants.GETSPAREDETAIL_FOR_PARTICULAR_JOBCARD);
				final List<SoldSpares> sparesSoldList = jdbctemp.query(
						dao_constants.GETSPAREDETAIL_FOR_PARTICULAR_JOBCARD,
						new Object[] { jobcardDetails.getJobcardNumber() },
						new RowMapper<SoldSpares>() {

							@Override
							public SoldSpares mapRow(ResultSet rs, int arg1)
									throws SQLException {
								// TODO Auto-generated method stub
								SoldSpares soldSpares = new SoldSpares();
								soldSpares.setSpareCode(rs.getString(
										"spare_code").trim());
								soldSpares.setCost(rs
										.getFloat("totalsparescost"));
								soldSpares.setQuantity(rs.getInt("quantity"));
								soldSpares.setSsId(rs.getInt("ssId"));
								soldSpares.setJobCardNo(rs
										.getString("jobcardnumber"));
								return soldSpares;
							}
						});
				log.info("SOLDSPARES SIZE : " + sparesSoldList.size());
				jdbctemp.queryForObject(
						dao_constants.GETDETAIL_FOR_PARTICULAR_JOBCARD,
						new Object[] { jobcardDetails.getJobcardNumber() },
						new RowMapper<JobCardDetails>() {

							@Override
							public JobCardDetails mapRow(ResultSet rs, int count)
									throws SQLException {
								jobcardDetails.setBranch(rs.getString("branch"));
								jobcardDetails.setChasisNumber(rs
										.getString("chasisnumber"));
								if (rs.getDate("closuredate") != null) {
									jobcardDetails.setClosureDate(ft.format(rs
											.getDate("closuredate")));
								} else {
									jobcardDetails.setClosureDate("");
								}
								jobcardDetails.setDateOfComplaint(ft.format(rs
										.getDate("dateofcomplaint")));
								jobcardDetails.setHours(rs.getInt("hours"));
								jobcardDetails.setLabourAmount(rs
										.getFloat("labouramount"));
								jobcardDetails.setNatureOfComplaint(rs
										.getString("natureofcomplaint"));
								jobcardDetails.setOverallCost(rs
										.getFloat("overallamount"));
								jobcardDetails.setActionTaken(rs
										.getString("actiontaken"));
								jobcardDetails.setReceiptNumber(rs
										.getString("receiptNumber"));
								jobcardDetails.setServiceComplaint(rs
										.getString("servicecomplaint"));
								jobcardDetails.setTotalSpareCost(rs
										.getFloat("sparestotalamount"));
								jobcardDetails.setServiceEngineerCode(rs
										.getString("eid"));
								jobcardDetails.setServiceType(rs
										.getString("typeofservice"));
								jobcardDetails.setSpareDetails(sparesSoldList);
								return jobcardDetails;
							}
						});

				message = "success";
			} catch (org.springframework.dao.EmptyResultDataAccessException aa) {
				log.debug("Exception at getReportForParticularJobcard : " + aa);
				// System.out.println("DATA IS NULL !!!! FOR CURRENT DATA");
			} catch (Exception ex) {
				log.debug("Exception Fetch jobcard details : " + ex);
				System.out.println("Exception getReportForParticularJobcard : "
						+ ex);
			}

		}
		return message;
	}

	public void getJobCardDetails(final JobCardDetails jobcardDetails) {
		log.info("Entered get repor for particular jobcard DAO");
		if (jdbctemp != null) {
			try {
				log.info("QUERY ::: "
						+ dao_constants.GETDETAIL_FOR_PARTICULAR_JOBCARD);
				log.info("QUERY 1 ::: "
						+ dao_constants.GETSPAREDETAIL_FOR_PARTICULAR_JOBCARD);
				final List<SoldSpares> sparesSoldList = jdbctemp.query(
						dao_constants.GETSPAREDETAIL_FOR_PARTICULAR_JOBCARD,
						new Object[] { jobcardDetails.getJobcardNumber() },
						new RowMapper<SoldSpares>() {

							@Override
							public SoldSpares mapRow(ResultSet rs, int arg1)
									throws SQLException {
								// TODO Auto-generated method stub
								SoldSpares soldSpares = new SoldSpares();
								soldSpares.setSpareCode(rs.getString(
										"spare_code").trim());
								soldSpares.setCost(rs
										.getFloat("totalsparescost"));
								soldSpares.setQuantity(rs.getInt("quantity"));
								soldSpares.setSsId(rs.getInt("ssId"));
								soldSpares.setJobCardNo(rs
										.getString("jobcardnumber"));
								return soldSpares;
							}
						});
				log.info("SOLDSPARES SIZE : " + sparesSoldList.size());
				jdbctemp.queryForObject(
						dao_constants.GETDETAIL_FOR_PARTICULAR_JOBCARD,
						new Object[] { jobcardDetails.getJobcardNumber() },
						new RowMapper<JobCardDetails>() {

							@Override
							public JobCardDetails mapRow(ResultSet rs, int count)
									throws SQLException {
								jobcardDetails.setBranch(rs.getString("branch"));
								jobcardDetails.setChasisNumber(rs
										.getString("chasisnumber"));
								if (rs.getDate("closuredate") != null) {
									jobcardDetails.setClosureDate(ft.format(rs
											.getDate("closuredate")));
								} else {
									jobcardDetails.setClosureDate("");
								}
								jobcardDetails.setDateOfComplaint(ft.format(rs
										.getDate("dateofcomplaint")));
								jobcardDetails.setHours(rs.getInt("hours"));
								jobcardDetails.setLabourAmount(rs
										.getFloat("labouramount"));
								jobcardDetails.setNatureOfComplaint(rs
										.getString("natureofcomplaint"));
								jobcardDetails.setOverallCost(rs
										.getFloat("overallamount"));
								jobcardDetails.setActionTaken(rs
										.getString("actiontaken"));
								jobcardDetails.setReceiptNumber(rs
										.getString("receiptNumber"));
								jobcardDetails.setServiceComplaint(rs
										.getString("servicecomplaint"));
								jobcardDetails.setTotalSpareCost(rs
										.getFloat("sparestotalamount"));
								jobcardDetails.setServiceEngineerCode(rs
										.getString("eid"));
								jobcardDetails.setServiceType(rs
										.getString("typeofservice"));
								jobcardDetails.setSpareDetails(sparesSoldList);
								return jobcardDetails;
							}
						});
			} catch (Exception ex) {
				log.fatal("Exception Fetching Data : " + ex);

			}
		}

	}

	@Override
	public String updateJobcard(final JobCardDetails jobcardDetails) {
		//TransactionDefinition def = new DefaultTransactionDefinition();
		//TransactionStatus status = transactionManager.getTransaction(def);
		TransactionTemplate template = new TransactionTemplate(transactionManager);
		
		String message = (String) template.execute(new TransactionCallback<Object>() {

			@Override
			public Object doInTransaction(TransactionStatus status) {
				// TODO Auto-generated method stub
				String message = "Failure";
				Boolean flag = deleteSparesForJobcard(jobcardDetails);
				if (flag) {
					jdbctemp.update(
							dao_constants.UPDATE_JOBCARD,
							new Object[] { jobcardDetails.getBranch(),
									jobcardDetails.getDateOfComplaint(),
									jobcardDetails.getChasisNumber(),
									jobcardDetails.getNatureOfComplaint(),
									jobcardDetails.getActionTaken(),
									jobcardDetails.getHours(),
									jobcardDetails.getClosureDate(),
									jobcardDetails.getLabourAmount(),
									jobcardDetails.getTotalSpareCost(),
									jobcardDetails.getServiceComplaint(),
									jobcardDetails.getOverallCost(),
									jobcardDetails.getReceiptNumber(),
									jobcardDetails.getServiceEngineerCode(),
									jobcardDetails.getServiceType(),
									jobcardDetails.getJobcardNumber() });
					List<SoldSpares> soldSparesList = jobcardDetails
							.getSpareDetails();
					jdbctemp.update(
							dao_constants.UPDATE_DATA_SERVICE,
							new Object[] { jobcardDetails.getChasisNumber(),
									jobcardDetails.getServiceType(),
									jobcardDetails.getClosureDate(),
									jobcardDetails.getJobcardNumber(),
									jobcardDetails.getHours(),
									jobcardDetails.getOverallCost(),
									jobcardDetails.getJobcardNumber() });

					int sizeOfSparesSold = soldSparesList.size();
					log.info("Size of Spared to Update : " + sizeOfSparesSold);
					for (SoldSpares soldSpares : soldSparesList) {
						jdbctemp.update(
								dao_constants.ADD_SOLDSPARE_DETAIL,
								new Object[] { jobcardDetails.getJobcardNumber(),
										soldSpares.getSpareCode().trim(),
										soldSpares.getCost(),
										soldSpares.getQuantity(),
										soldSpares.getTotal(),
										jobcardDetails.getReceiptNumber() });
					}
					log.info("Completed UPDATE !!!!");
					message = "success";
					try {
						jdbctemp.getDataSource().getConnection().commit();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					transactionManager.commit(status);
				}
				return message;
			}
		});
		
	/*	String message = "Failure";
		log.info("--- Entered the Update JobCard Details DAO ---");
		log.info("QUERY ::: " + dao_constants.UPDATE_JOBCARD);
		log.info("QUERY 1 ::: " + dao_constants.UPDATE_SPARESSOLD);
		log.info("QUERY 1 ::: " + dao_constants.UPDATE_DATA_SERVICE);

		try {
			jdbctemp.getDataSource().getConnection().setAutoCommit(false);
			Boolean flag = deleteSparesForJobcard(jobcardDetails);
			if (flag) {
				jdbctemp.update(
						dao_constants.UPDATE_JOBCARD,
						new Object[] { jobcardDetails.getBranch(),
								jobcardDetails.getDateOfComplaint(),
								jobcardDetails.getChasisNumber(),
								jobcardDetails.getNatureOfComplaint(),
								jobcardDetails.getActionTaken(),
								jobcardDetails.getHours(),
								jobcardDetails.getClosureDate(),
								jobcardDetails.getLabourAmount(),
								jobcardDetails.getTotalSpareCost(),
								jobcardDetails.getServiceComplaint(),
								jobcardDetails.getOverallCost(),
								jobcardDetails.getReceiptNumber(),
								jobcardDetails.getServiceEngineerCode(),
								jobcardDetails.getServiceType(),
								jobcardDetails.getJobcardNumber() });
				List<SoldSpares> soldSparesList = jobcardDetails
						.getSpareDetails();
				jdbctemp.update(
						dao_constants.UPDATE_DATA_SERVICE,
						new Object[] { jobcardDetails.getChasisNumber(),
								jobcardDetails.getServiceType(),
								jobcardDetails.getClosureDate(),
								jobcardDetails.getJobcardNumber(),
								jobcardDetails.getHours(),
								jobcardDetails.getOverallCost(),
								jobcardDetails.getJobcardNumber() });

				int sizeOfSparesSold = soldSparesList.size();
				log.info("Size of Spared to Update : " + sizeOfSparesSold);
				for (SoldSpares soldSpares : soldSparesList) {
					jdbctemp.update(
							dao_constants.ADD_SOLDSPARE_DETAIL,
							new Object[] { jobcardDetails.getJobcardNumber(),
									soldSpares.getSpareCode().trim(),
									soldSpares.getCost(),
									soldSpares.getQuantity(),
									soldSpares.getTotal(),
									jobcardDetails.getReceiptNumber() });
				}
				log.info("Completed UPDATE !!!!");
				message = "success";
				jdbctemp.getDataSource().getConnection().commit();
				transactionManager.commit(status);
			}
			return message;
		} catch (Exception e) {
			try {
				jdbctemp.getDataSource().getConnection().rollback();
				transactionManager.rollback(status);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			log.fatal("Exception in updateing the JOBCARD : " + e
					+ " For Chasis Number :" + jobcardDetails.getChasisNumber());
			return message;
		}
	*/
		return message;
	}

	@Override
	public Boolean deleteSparesForJobcard(JobCardDetails jobcardDetails) {
		// TODO Auto-generated method stub
		Boolean clearFlag = new Boolean(false);
		try {
			jdbctemp.update(dao_constants.DELETE_SPARESDETAILS,
					new Object[] { jobcardDetails.getJobcardNumber() });
			clearFlag = true;
		} catch (Exception e) {
			log.fatal("Exception in clearing the sold spared details for jobcard : "
					+ jobcardDetails.getJobcardNumber() + " Exception :" + e);
			clearFlag = false;
		}
		return clearFlag;
	}

	public List<PendingService> getCompleteServicePendinglist(String choosenDate) {
		log.info("Entered DAO getCompleteServicePendingList");
		List<String> columnNames = queryList.getColumnNames();
		List<String> serviceTypes = queryList.getServiceType();
		List<PendingService> listOfPendingServices = new ArrayList<PendingService>();

		for (int i = 0; i < columnNames.size(); i++) {
			String _queryToFetchData = ((dao_constants.SELECT_COMPLETE_PENDINGSERVICE_BY_MONTH)
					.replace("COLUMN_NAME", columnNames.get(i))).replace(
					"SERVICE_TYPE", serviceTypes.get(i));
			log.debug("QUERY TO FETCH COMPLETE DATA : " + _queryToFetchData);
			log.debug("FETCH DATA FOR DATE : " + choosenDate);
			try{
			listOfPendingServices.addAll(

			(List<PendingService>) jdbctemp.query(_queryToFetchData,
					new Object[] { choosenDate, choosenDate },
					new RowMapper<PendingService>() {

						@Override
						public PendingService mapRow(ResultSet rs, int recCount)
								throws SQLException {
							// TODO Auto-generated method stub
							PendingService ps = new PendingService();
							ps.setChasisNumber(rs.getString(1));
							ps.setActual_date_of_service(rs.getDate(2));
							ps.setCustomerDetails(rs.getString(3).trim());
							ps.setServiceName(rs.getString(4));
							return ps;
						}
					})

			);
			}catch(Exception ex){
				System.out.println("Exception fetching data : Query :" + _queryToFetchData+"  choosenDate : "+choosenDate);
				log.debug("Exception fetching data : Query :" + _queryToFetchData+"  choosenDate : "+choosenDate);
			}
		}

		return listOfPendingServices;
	}

	@Override
	public List<EmployeeDetails> getEmployeeID() {
		// TODO Auto-generated method stub
		return (List<EmployeeDetails>) jdbctemp.query(
				dao_constants.GET_EMPLOYEE_ID,
				new RowMapper<EmployeeDetails>() {
					@Override
					public EmployeeDetails mapRow(ResultSet rs, int recCount)
							throws SQLException {
						// TODO Auto-generated method stub
						EmployeeDetails ps = new EmployeeDetails();
						ps.setEmployeeId(rs.getString(1));
						ps.setEmployeeName(rs.getString(2));
						ps.setBaseSalary(rs.getDouble(3));
						return ps;
					}
				});
	}
	@Override
	public List<String> getEmployeeIDString() {
		// TODO Auto-generated method stub
		return jdbctemp.queryForList(dao_constants.GET_EMPLOYEE_ID_ONLY, String.class);
		}
	@Override	
	public double getAssociateBaseSalary(String eid,String queryDate){
		Date date = new Date();
		try{
			if(queryDate !=null){
				date = queryFormat.parse(queryDate);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		if(queryDate==null){
			return jdbctemp.queryForObject(dao_constants.SELECT_SALARY_FOR_EID_A, new Object[]{eid}, Double.class);	
		}else
		{
			return jdbctemp.queryForObject(dao_constants.SELECT_SALARY_FOR_EID_B, new Object[]{eid,ft.format(date)}, Double.class);
		}
		
		
	}	
//public List<DaDetails> getDADetails()	

	@Override
	public List<org.races.model.DaDetails> getDADetails() {
		// TODO Auto-generated method stub
		return (List<org.races.model.DaDetails>) jdbctemp.query(
				dao_constants.SELECT_DA_DETAILS,
				new RowMapper<org.races.model.DaDetails>() {
					@Override
					public org.races.model.DaDetails mapRow(ResultSet rs, int recCount)
							throws SQLException {
						// TODO Auto-generated method stub
						org.races.model.DaDetails ps = new org.races.model.DaDetails();
						ps.setDaType(rs.getString(1));
						ps.setAmount(rs.getDouble(2)); 
						return ps;
					}
				});
	}	

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.races.dao.RacesDao#addCustomerDetails(org.races.model.customerDetails
	 * )
	 */
	@Override
	public String addCustomerDetails(final CustomerDetails customerDetails) {
		String message = "Failure";
		if (customerDetails.getInstalledDate().isEmpty()) {
			customerDetails.setInstalledDate("1900-01-01");
		}
		System.out.println("started add customer details method !!!!");
		System.out.println(customerDetails.getTaluk());
		System.out.println(customerDetails.getDistrict());
		System.out.println(customerDetails.getContactNumber());
		System.out.println(customerDetails.getNameaddr());
		System.out.println(customerDetails.getChasisNumber());
		System.out.println(customerDetails.getEngineNumber());
		System.out.println(customerDetails.getDateOFsale());
		System.out.println(customerDetails.getInstalledDate());
		System.out.println(customerDetails.getRegisteredBranch());
		System.out.println(customerDetails.getDelivaryBranch());
		System.out.println(customerDetails.getPostOffice());
		System.out.println(customerDetails.getPincode());
		System.out.println(customerDetails.getCustomerId());
		System.out.println(customerDetails.getServiceEngineerCode());
		System.out
				.println("ft.format(new Date(customerDetails.getDateOFsale())) :: "
						+ ft.format(new Date(customerDetails.getDateOFsale()
								.replace('-', '/'))));
		System.out
				.println("ft.format(new Date(customerDetails.getInstalledDate())) ::"
						+ ft.format(new Date(customerDetails.getInstalledDate()
								.replace('-', '/'))));

		System.out.println("**************************************");
		try {
			System.out.println("Entered add customer details !!!!");
			if (customerDetails.isAutoincrement()) {
				int customerId = generateCustomerId();
				customerDetails.setCustomerId(customerId);

			}
			System.out.println("QUERY :" + dao_constants.INSERT_CUSTOMER);

			int rowCount = jdbctemp.update(
					dao_constants.INSERT_CUSTOMER,
					// TODO Auto-generated method stub
					new Object[] {
							customerDetails.getTaluk(),
							customerDetails.getDistrict(),
							customerDetails.getContactNumber(),
							customerDetails.getNameaddr(),
							customerDetails.getChasisNumber(),
							customerDetails.getEngineNumber(),
							ft.format(new Date(customerDetails.getDateOFsale()
									.replace('-', '/'))),
							ft.format(new Date(customerDetails
									.getInstalledDate().replace('-', '/'))),
							customerDetails.getRegisteredBranch(),
							customerDetails.getDelivaryBranch(),
							customerDetails.getPostOffice(),
							customerDetails.getPincode(),
							customerDetails.getCustomerId(),
							customerDetails.getServiceEngineerCode() });

			System.out.println("Customer added count : "+rowCount);
			System.out.println("ACTUAL SERVICE QUERY :"+dao_constants.INSERT_SERVICE_ACTUAL);
			rowCount = jdbctemp.update(
					dao_constants.INSERT_SERVICE_ACTUAL,
					new Object[] { customerDetails.getChasisNumber(),
							customerDetails.getDateOFsale(),
							customerDetails.getDateOFsale(),
							customerDetails.getDateOFsale(),
							customerDetails.getDateOFsale(),
							customerDetails.getDateOFsale(),
							customerDetails.getDateOFsale(),
							customerDetails.getDateOFsale(),
							customerDetails.getDateOFsale(),
							customerDetails.getDateOFsale() });
			System.out.println("Exiting add customer details !!!!");
			System.out.println("Customer actuals table added count : "+rowCount);
			
			message = "success";
		} catch (DataIntegrityViolationException duplicateEntry) {
			message = "DuplicateEntry : " + duplicateEntry.getMessage();
			log.fatal("DuplicateEntry :: Exception while inserting Customer Data : "
					+ duplicateEntry);

		} catch (Exception exception) {
			// log.fatal("Exception while inserting Customer Data   : "+exception.printStackTrace());
			exception.printStackTrace();
			// System.out.println();
		}
		return message;
	}

	/**
	 * Generate the customerId if autoincrement value is ture.
	 * 
	 * @return customerId of the customer.
	 */
	private int generateCustomerId() {
		log.info("Entered into generateCustomerId");
		int customerId = 0;
		int count = (int) jdbctemp.queryForObject(dao_constants.SELECT_COUNT,
				new Object[] {}, Integer.class);
		if (count != 0) {
			customerId = (int) jdbctemp.queryForObject(
					dao_constants.SELECT_MAXCUSTOMERID, new Object[] {},
					Integer.class);
		} else {
			customerId++;
		}
		log.info("Customer Id: " + customerId);
		log.info("Existing from generateCustomerId");
		return customerId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.races.dao.RacesDao#updateCustomerDetails(org.races.model.customerDetails
	 * )
	 */
	@Override
	public String updateCustomerDetails(final CustomerDetails customerDetails) {
		 String message = "Failure";
		//TransactionDefinition def = new DefaultTransactionDefinition();
		//TransactionStatus status = transactionManager.getTransaction(def);
			if(customerDetails.getInstalledDate() == null | customerDetails.getInstalledDate() == "" )
			{
				customerDetails.setInstalledDate("1111-11-11");
			}
		TransactionTemplate template = new TransactionTemplate(transactionManager);
		message = (String) template.execute(new TransactionCallback<Object>() {
			  public Object doInTransaction(TransactionStatus transactionStatus) {
				  int rowCount = jdbctemp.update(dao_constants.UPDATE_CUSTOMER,
							new PreparedStatementSetter() {

								@Override
								public void setValues(PreparedStatement ps)
										throws SQLException {
									// TODO Auto-generated method stub
									ps.setInt(1, customerDetails.getCustomerId());
									ps.setString(2, customerDetails.getTaluk());
									ps.setString(3, customerDetails.getPostOffice());
									ps.setString(4, customerDetails.getDistrict());
									ps.setString(5, customerDetails.getContactNumber());
									ps.setString(6, customerDetails.getNameaddr());
									ps.setString(7, customerDetails.getChasisNumber());
									ps.setString(8, customerDetails.getPincode());
									ps.setString(9, customerDetails.getEngineNumber());	
									ps.setString(10, ft.format(new Date(customerDetails
											.getDateOFsale().replace('-', '/'))));
									if(customerDetails.getInstalledDate()!=null)
									{
									ps.setString(11, ft.format(new Date(customerDetails
											.getInstalledDate().replace('-', '/'))));
									}
									ps.setString(12,
											customerDetails.getRegisteredBranch());
									ps.setString(13,
											customerDetails.getDelivaryBranch());
									ps.setString(14,
											customerDetails.getServiceEngineerCode());
									ps.setString(15, customerDetails.getChasisNumber());
								}

							});
					System.out.println("updated customer details : rows updated : "+rowCount);
					rowCount = jdbctemp.update(dao_constants.UPDATE_SERVICE_ACTUAL,new Object[]{
							customerDetails.getChasisNumber(),
							customerDetails.getDateOFsale(),
							customerDetails.getDateOFsale(),
							customerDetails.getDateOFsale(),
							customerDetails.getDateOFsale(),
							customerDetails.getDateOFsale(),
							customerDetails.getDateOFsale(),
							customerDetails.getDateOFsale(),
							customerDetails.getDateOFsale(),
							customerDetails.getDateOFsale(),
							customerDetails.getChasisNumber()
					});

					System.out.println("updated customer service actuals details : rows updated : "+rowCount);
					transactionManager.commit(transactionStatus);
					return "success";
			  }
			});
		

		/*try {

			jdbctemp.getDataSource().getConnection().setAutoCommit(false); 
			int rowCount = jdbctemp.update(dao_constants.UPDATE_CUSTOMER,
					new PreparedStatementSetter() {

						@Override
						public void setValues(PreparedStatement ps)
								throws SQLException {
							// TODO Auto-generated method stub
							ps.setInt(1, customerDetails.getCustomerId());
							ps.setString(2, customerDetails.getTaluk());
							ps.setString(3, customerDetails.getPostOffice());
							ps.setString(4, customerDetails.getDistrict());
							ps.setString(5, customerDetails.getContactNumber());
							ps.setString(6, customerDetails.getNameaddr());
							ps.setString(7, customerDetails.getChasisNumber());
							ps.setString(8, customerDetails.getPincode());
							ps.setString(9, customerDetails.getEngineNumber());	
							ps.setString(10, ft.format(new Date(customerDetails
									.getDateOFsale().replace('-', '/'))));
							if(customerDetails.getInstalledDate()!=null)
							{
							ps.setString(11, ft.format(new Date(customerDetails
									.getInstalledDate().replace('-', '/'))));
							}
							ps.setString(12,
									customerDetails.getRegisteredBranch());
							ps.setString(13,
									customerDetails.getDelivaryBranch());
							ps.setString(14,
									customerDetails.getServiceEngineerCode());
							ps.setString(15, customerDetails.getChasisNumber());
						}

					});
			System.out.println("updated customer details : rows updated : "+rowCount);
			rowCount = jdbctemp.update(dao_constants.UPDATE_SERVICE_ACTUAL,new Object[]{
					customerDetails.getChasisNumber(),
					customerDetails.getDateOFsale(),
					customerDetails.getDateOFsale(),
					customerDetails.getDateOFsale(),
					customerDetails.getDateOFsale(),
					customerDetails.getDateOFsale(),
					customerDetails.getDateOFsale(),
					customerDetails.getDateOFsale(),
					customerDetails.getDateOFsale(),
					customerDetails.getDateOFsale(),
					customerDetails.getChasisNumber()
			});

			System.out.println("updated customer service actuals details : rows updated : "+rowCount);
			message = "success";
			jdbctemp.getDataSource().getConnection().commit();
			transactionManager.commit(status);
		} catch (DataIntegrityViolationException duplicateEntry) {
			message = "DuplicateEntry : " + duplicateEntry.getMessage();
			log.fatal("Exception while Updating Customer Data : "
					+ duplicateEntry);
			try {
				jdbctemp.getDataSource().getConnection().rollback();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			transactionManager.rollback(status);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		return message;
	}

	@Override
	public CustomerDetails getReportForParticularChasisNumber(
			String chasisNumber, final CustomerDetails customerDetails) {
		// String message ="Failure";
		if (jdbctemp != null) {
			try {
				jdbctemp.queryForObject(dao_constants.SELECT_CUSTOMER,
						new Object[] { chasisNumber },
						new RowMapper<CustomerDetails>() {
							@Override
							public CustomerDetails mapRow(ResultSet rs,
									int count) throws SQLException {
								customerDetails.setCustomerId(rs
										.getInt("customerId"));
								customerDetails.setChasisNumber(rs
										.getString("chasisNumber"));
								customerDetails.setEngineNumber(rs
										.getString("engineNumber"));
								customerDetails.setDistrict(rs
										.getString("district"));
								customerDetails.setContactNumber(rs
										.getString("contact_no"));
								customerDetails.setDelivaryBranch(rs
										.getString("delivaryBranch"));
								customerDetails.setRegisteredBranch(rs
										.getString("registeredBranch"));
								customerDetails.setDateOFsale(ft.format(rs
										.getDate("dateOFsale")));
								if(rs.getDate("installedDate") != null) {
									  customerDetails.setInstalledDate(ft.format(rs.getDate("installedDate")));
									}
								else
								{
									//customerDetails.setInstalledDate("0000-00-00");
								}
								customerDetails.setNameaddr(rs
										.getString("name_address"));
								customerDetails.setPincode(rs
										.getString("pincode"));
								customerDetails.setTaluk(rs.getString("Taluk"));
								customerDetails.setPostOffice(rs
										.getString(3));
								customerDetails.setServiceEngineerCode(rs
										.getString("employeeid"));
								return customerDetails;
							}
						});
				customerDetails.setMessage("success");
			} catch (org.springframework.dao.EmptyResultDataAccessException ex) {
				customerDetails.setMessage("No Records Found");
			} catch (Exception ex) {
				log.fatal("Exception Fetching Data : " + ex);
			}
		}

		return customerDetails;
	}

	/*
	 * Method to get the pending report based on taluk or district.
	 * 
	 * @see
	 * org.races.dao.RacesDao#getExportedStatus(org.races.model.ReportFilter)
	 */
	@Override
	public Map<String, ArrayList<PendingService>> getExportedStatus(
			ReportFilter reportFilter,
			Map<String, ArrayList<PendingService>> filterBasePojos) {
		// Map<String, List<PendingService>> filterBasePojos = new
		// LinkedHashMap<String, List<PendingService>>();
		if (reportFilter.getExportTypeFilter() == ExportFilterValue.TALUK) {
			List<String> getTalukList = queryList.getTaluk();

			for (String talukName : getTalukList) {
				List<PendingService> listofPojos = new ArrayList<PendingService>();
				List<PendingService> tempPojos = new ArrayList<PendingService>();
				reportFilter.setTalukName(talukName);
				tempPojos = getPendingFirstServiceForSeletedMonthByTaluk(reportFilter);
				log.fatal("Size of 1 : " + talukName + "  size : "
						+ tempPojos.size());
				listofPojos.addAll(tempPojos);
				tempPojos.clear();
				tempPojos = getPendingSecondServiceForSeletedMonthByTaluk(reportFilter);
				log.fatal("Size of 2 : " + talukName + "  size : "
						+ tempPojos.size());
				listofPojos.addAll(tempPojos);
				tempPojos.clear();
				tempPojos = getPendingThirdServiceForSeletedMonthByTaluk(reportFilter);
				log.fatal("Size of 3 : " + talukName + "  size : "
						+ tempPojos.size());
				listofPojos.addAll(tempPojos);
				tempPojos.clear();
				tempPojos = getPendingFourthServiceForSeletedMonthByTaluk(reportFilter);
				log.fatal("Size of 4 : " + talukName + "  size : "
						+ tempPojos.size());
				listofPojos.addAll(tempPojos);
				tempPojos.clear();
				tempPojos = getPendingFifthServiceForSeletedMonthByTaluk(reportFilter);
				log.fatal("Size of 5 : " + talukName + "  size : "
						+ tempPojos.size());
				listofPojos.addAll(tempPojos);
				tempPojos.clear();
				tempPojos = getPendingSixthServiceForSeletedMonthByTaluk(reportFilter);
				log.fatal("Size of 6 : " + talukName + "  size : "
						+ tempPojos.size());
				listofPojos.addAll(tempPojos);
				tempPojos.clear();
				tempPojos = getPendingSeventhServiceForSeletedMonthByTaluk(reportFilter);
				log.fatal("Size of 7 : " + talukName + "  size : "
						+ tempPojos.size());
				listofPojos.addAll(tempPojos);
				tempPojos.clear();
				tempPojos = getPendingEigthServiceForSeletedMonthByTaluk(reportFilter);
				log.fatal("Size of 8 : " + talukName + "  size : "
						+ tempPojos.size());
				listofPojos.addAll(tempPojos);
				tempPojos.clear();
				if (listofPojos.size() == 0) {
					continue;
				} else {
					log.fatal("Total pojo for : " + talukName + " Is :"
							+ listofPojos.size());
					filterBasePojos.put(talukName,
							new ArrayList<PendingService>(listofPojos));
					listofPojos.clear();
				}
			}

		} else if (reportFilter.getExportTypeFilter() == ExportFilterValue.DISTRICT) {
			List<String> getDistrictList = queryList.getDistricts();

			for (String districtName : getDistrictList) {
				List<PendingService> listofPojos = new ArrayList<PendingService>();
				List<PendingService> tempPojos = new ArrayList<PendingService>();
				reportFilter.setDistrictName(districtName);
				tempPojos = getPendingFirstServiceForSeletedMonthByDistrict(reportFilter);
				log.fatal("Size of 1 : " + districtName + "  size : "
						+ tempPojos.size());
				listofPojos.addAll(tempPojos);
				tempPojos.clear();
				tempPojos = getPendingSecondServiceForSeletedMonthByDistrict(reportFilter);
				log.fatal("Size of 2 : " + districtName + "  size : "
						+ tempPojos.size());
				listofPojos.addAll(tempPojos);
				tempPojos.clear();
				tempPojos = getPendingThirdServiceForSeletedMonthByDistrict(reportFilter);
				log.fatal("Size of 3 : " + districtName + "  size : "
						+ tempPojos.size());
				listofPojos.addAll(tempPojos);
				tempPojos.clear();
				tempPojos = getPendingFourthServiceForSeletedMonthByDistrict(reportFilter);
				log.fatal("Size of 4 : " + districtName + "  size : "
						+ tempPojos.size());
				listofPojos.addAll(tempPojos);
				tempPojos.clear();
				tempPojos = getPendingFifthServiceForSeletedMonthByDistrict(reportFilter);
				log.fatal("Size of 5 : " + districtName + "  size : "
						+ tempPojos.size());
				listofPojos.addAll(tempPojos);
				tempPojos.clear();
				tempPojos = getPendingSixthServiceForSeletedMonthByDistrict(reportFilter);
				log.fatal("Size of 6 : " + districtName + "  size : "
						+ tempPojos.size());
				listofPojos.addAll(tempPojos);
				tempPojos.clear();
				tempPojos = getPendingSeventhServiceForSeletedMonthByDistrict(reportFilter);
				log.fatal("Size of 7 : " + districtName + "  size : "
						+ tempPojos.size());
				listofPojos.addAll(tempPojos);
				tempPojos.clear();
				tempPojos = getPendingEigthServiceForSeletedMonthByDistrict(reportFilter);
				log.fatal("Size of 8 : " + districtName + "  size : "
						+ tempPojos.size());
				listofPojos.addAll(tempPojos);
				tempPojos.clear();
				if (listofPojos.size() == 0) {
					continue;
				} else {
					log.fatal("Total pojo for : " + districtName + " Is :"
							+ listofPojos.size());
					filterBasePojos.put(districtName,
							new ArrayList<PendingService>(listofPojos));
					listofPojos.clear();
				}
			}
		}
		return filterBasePojos;
	}

	/*
	 * Following 8 methods are for fetching the data of pending report -
	 * specific to Taluk as Filter.
	 */
	public List<PendingService> getPendingFirstServiceForSeletedMonthByTaluk(
			ReportFilter reportFilter) {
		Object param[] = getQueryParam(reportFilter);
		log.debug("---> param.length : " + param.length);
		String query = null;
		log.info("Param:     " + param[0]);
		log.debug("Param Array:     " + param);
		if (ReportConstants.DURATION_REPORT.equals(reportFilter.getFrom())
				&& !ReportConstants.NAVIGATE_LINK.equals(reportFilter
						.getNavLink())) {
			query = dao_constants.SELECT_SERVICE_PENDING_BYMONTH_BYTALUK_FIRST;
		} else {
			query = dao_constants.SELECT_SERVICE_PENDING_BYSELECTED_MONTH_TALUK_FIRST;
		}
		log.info("QUERY:" + query);
		return (List<PendingService>) jdbctemp.query(query, param,
				new RowMapper<PendingService>() {

					@Override
					public PendingService mapRow(ResultSet rs, int recCount)
							throws SQLException {
						// TODO Auto-generated method stub
						PendingService ps = new PendingService();
						ps.setChasisNumber(rs.getString("CHASISNUMBER"));
						ps.setActual_date_of_service(rs.getDate("SERVICE_DATE"));
						ps.setCustomerDetails((rs.getString("NAME_ADDR").trim() + ","
								+ rs.getString("POST_OFFICE") + ","
								+ rs.getString("TALUK") + ","
								+ rs.getString("DISTRICT") + ","
								+ rs.getString("PINCODE") + "," + rs
								.getString("CONTACT_NO")).trim());
						ps.setServiceName(rs.getString("SERVICE_NAME"));
						return ps;
					}
				});
	}

	public List<PendingService> getPendingSecondServiceForSeletedMonthByTaluk(
			ReportFilter reportFilter) {
		Object param[] = getQueryParam(reportFilter);
		String query = null;
		if (ReportConstants.DURATION_REPORT.equals(reportFilter.getFrom())
				&& !ReportConstants.NAVIGATE_LINK.equals(reportFilter
						.getNavLink())) {
			query = dao_constants.SELECT_SERVICE_PENDING_BYMONTH_BYTALUK_SECOND;
		} else {
			query = dao_constants.SELECT_SERVICE_PENDING_BYSELECTED_MONTH_TALUK_SECOND;
		}
		return (List<PendingService>) jdbctemp.query(query, param,
				new RowMapper<PendingService>() {

					@Override
					public PendingService mapRow(ResultSet rs, int recCount)
							throws SQLException {
						// TODO Auto-generated method stub
						PendingService ps = new PendingService();
						ps.setChasisNumber(rs.getString("CHASISNUMBER"));
						ps.setActual_date_of_service(rs.getDate("SERVICE_DATE"));
						ps.setCustomerDetails((rs.getString("NAME_ADDR") + ","
								+ rs.getString("POST_OFFICE") + ","
								+ rs.getString("TALUK") + ","
								+ rs.getString("DISTRICT") + ","
								+ rs.getString("PINCODE") + "," + rs
								.getString("CONTACT_NO")).trim());
						ps.setServiceName(rs.getString("SERVICE_NAME"));
						return ps;
					}
				});
	}

	public List<PendingService> getPendingThirdServiceForSeletedMonthByTaluk(
			ReportFilter reportFilter) {
		Object param[] = getQueryParam(reportFilter);
		String query = null;
		if (ReportConstants.DURATION_REPORT.equals(reportFilter.getFrom())
				&& !ReportConstants.NAVIGATE_LINK.equals(reportFilter
						.getNavLink())) {
			query = dao_constants.SELECT_SERVICE_PENDING_BYMONTH_BYTALUK_THIRD;
		} else {
			query = dao_constants.SELECT_SERVICE_PENDING_BYSELECTED_MONTH_TALUK_THIRD;
		}
		return (List<PendingService>) jdbctemp.query(query, param,
				new RowMapper<PendingService>() {

					@Override
					public PendingService mapRow(ResultSet rs, int recCount)
							throws SQLException {
						// TODO Auto-generated method stub
						PendingService ps = new PendingService();
						ps.setChasisNumber(rs.getString("CHASISNUMBER"));
						ps.setActual_date_of_service(rs.getDate("SERVICE_DATE"));
						ps.setCustomerDetails((rs.getString("NAME_ADDR").trim() + ","
								+ rs.getString("POST_OFFICE") + ","
								+ rs.getString("TALUK") + ","
								+ rs.getString("DISTRICT") + ","
								+ rs.getString("PINCODE") + "," + rs
								.getString("CONTACT_NO")).trim());
						ps.setServiceName(rs.getString("SERVICE_NAME"));
						return ps;
					}
				});
	}

	public List<PendingService> getPendingFourthServiceForSeletedMonthByTaluk(
			ReportFilter reportFilter) {
		Object param[] = getQueryParam(reportFilter);
		String query = null;
		if (ReportConstants.DURATION_REPORT.equals(reportFilter.getFrom())
				&& !ReportConstants.NAVIGATE_LINK.equals(reportFilter
						.getNavLink())) {
			query = dao_constants.SELECT_SERVICE_PENDING_BYMONTH_BYTALUK_FOURTH;
		} else {
			query = dao_constants.SELECT_SERVICE_PENDING_BYSELECTED_MONTH_TALUK_FOURTH;
		}
		return (List<PendingService>) jdbctemp.query(query, param,
				new RowMapper<PendingService>() {

					@Override
					public PendingService mapRow(ResultSet rs, int recCount)
							throws SQLException {
						// TODO Auto-generated method stub
						PendingService ps = new PendingService();
						ps.setChasisNumber(rs.getString("CHASISNUMBER"));
						ps.setActual_date_of_service(rs.getDate("SERVICE_DATE"));
						ps.setCustomerDetails((rs.getString("NAME_ADDR").trim() + ","
								+ rs.getString("POST_OFFICE") + ","
								+ rs.getString("TALUK") + ","
								+ rs.getString("DISTRICT") + ","
								+ rs.getString("PINCODE") + "," + rs
								.getString("CONTACT_NO")).trim());
						ps.setServiceName(rs.getString("SERVICE_NAME"));
						return ps;
					}
				});
	}

	public List<PendingService> getPendingFifthServiceForSeletedMonthByTaluk(
			ReportFilter reportFilter) {
		Object param[] = getQueryParam(reportFilter);
		String query = null;
		if (ReportConstants.DURATION_REPORT.equals(reportFilter.getFrom())
				&& !ReportConstants.NAVIGATE_LINK.equals(reportFilter
						.getNavLink())) {
			query = dao_constants.SELECT_SERVICE_PENDING_BYMONTH_BYTALUK_FIFTH;
		} else {
			query = dao_constants.SELECT_SERVICE_PENDING_BYSELECTED_MONTH_TALUK_FIFTH;
		}
		return (List<PendingService>) jdbctemp.query(query, param,
				new RowMapper<PendingService>() {

					@Override
					public PendingService mapRow(ResultSet rs, int recCount)
							throws SQLException {
						// TODO Auto-generated method stub
						PendingService ps = new PendingService();
						ps.setChasisNumber(rs.getString("CHASISNUMBER"));
						ps.setActual_date_of_service(rs.getDate("SERVICE_DATE"));
						ps.setCustomerDetails((rs.getString("NAME_ADDR") + ","
								+ rs.getString("POST_OFFICE") + ","
								+ rs.getString("TALUK") + ","
								+ rs.getString("DISTRICT") + ","
								+ rs.getString("PINCODE") + "," + rs
								.getString("CONTACT_NO")).trim());
						ps.setServiceName(rs.getString("SERVICE_NAME"));
						return ps;
					}
				});
	}

	public List<PendingService> getPendingSixthServiceForSeletedMonthByTaluk(
			ReportFilter reportFilter) {
		Object param[] = getQueryParam(reportFilter);
		String query = null;
		if (ReportConstants.DURATION_REPORT.equals(reportFilter.getFrom())
				&& !ReportConstants.NAVIGATE_LINK.equals(reportFilter
						.getNavLink())) {
			query = dao_constants.SELECT_SERVICE_PENDING_BYMONTH_BYTALUK_SIXTH;
		} else {
			query = dao_constants.SELECT_SERVICE_PENDING_BYSELECTED_MONTH_TALUK_SIXTH;
		}
		return (List<PendingService>) jdbctemp.query(query, param,
				new RowMapper<PendingService>() {

					@Override
					public PendingService mapRow(ResultSet rs, int recCount)
							throws SQLException {
						// TODO Auto-generated method stub
						PendingService ps = new PendingService();
						ps.setChasisNumber(rs.getString("CHASISNUMBER"));
						ps.setActual_date_of_service(rs.getDate("SERVICE_DATE"));
						ps.setCustomerDetails((rs.getString("NAME_ADDR") + ","
								+ rs.getString("POST_OFFICE") + ","
								+ rs.getString("TALUK") + ","
								+ rs.getString("DISTRICT") + ","
								+ rs.getString("PINCODE") + "," + rs
								.getString("CONTACT_NO")).trim());
						ps.setServiceName(rs.getString("SERVICE_NAME"));
						return ps;
					}
				});
	}

	public List<PendingService> getPendingSeventhServiceForSeletedMonthByTaluk(
			ReportFilter reportFilter) {
		Object param[] = getQueryParam(reportFilter);
		String query = null;
		if (ReportConstants.DURATION_REPORT.equals(reportFilter.getFrom())
				&& !ReportConstants.NAVIGATE_LINK.equals(reportFilter
						.getNavLink())) {
			query = dao_constants.SELECT_SERVICE_PENDING_BYMONTH_BYTALUK_SEVENTH;
		} else {
			query = dao_constants.SELECT_SERVICE_PENDING_BYSELECTED_MONTH_TALUK_SEVENTH;
		}
		return (List<PendingService>) jdbctemp.query(query, param,
				new RowMapper<PendingService>() {

					@Override
					public PendingService mapRow(ResultSet rs, int recCount)
							throws SQLException {
						// TODO Auto-generated method stub
						PendingService ps = new PendingService();
						ps.setChasisNumber(rs.getString("CHASISNUMBER"));
						ps.setActual_date_of_service(rs.getDate("SERVICE_DATE"));
						ps.setCustomerDetails((rs.getString("NAME_ADDR") + ","
								+ rs.getString("POST_OFFICE") + ","
								+ rs.getString("TALUK") + ","
								+ rs.getString("DISTRICT") + ","
								+ rs.getString("PINCODE") + "," + rs
								.getString("CONTACT_NO")).trim());
						ps.setServiceName(rs.getString("SERVICE_NAME"));
						return ps;
					}
				});
	}

	public List<PendingService> getPendingEigthServiceForSeletedMonthByTaluk(
			ReportFilter reportFilter) {
		Object param[] = getQueryParam(reportFilter);
		String query = null;
		if (ReportConstants.DURATION_REPORT.equals(reportFilter.getFrom())
				&& !ReportConstants.NAVIGATE_LINK.equals(reportFilter
						.getNavLink())) {
			query = dao_constants.SELECT_SERVICE_PENDING_BYMONTH_BYTALUK_EIGTH;
		} else {
			query = dao_constants.SELECT_SERVICE_PENDING_BYSELECTED_MONTH_TALUK_EIGTH;
		}
		return (List<PendingService>) jdbctemp.query(query, param,
				new RowMapper<PendingService>() {

					@Override
					public PendingService mapRow(ResultSet rs, int recCount)
							throws SQLException {
						// TODO Auto-generated method stub
						PendingService ps = new PendingService();
						ps.setChasisNumber(rs.getString("CHASISNUMBER"));
						ps.setActual_date_of_service(rs.getDate("SERVICE_DATE"));
						ps.setCustomerDetails((rs.getString("NAME_ADDR") + ","
								+ rs.getString("POST_OFFICE") + ","
								+ rs.getString("TALUK") + ","
								+ rs.getString("DISTRICT") + ","
								+ rs.getString("PINCODE") + "," + rs
								.getString("CONTACT_NO")).trim());
						ps.setServiceName(rs.getString("SERVICE_NAME"));
						return ps;
					}
				});
	}

	/****************** For getting the pending report specific to district *************************/

	/*
	 * Following 8 methods are for fetching the data of pending report -
	 * specific to Taluk as Filter.
	 */
	public List<PendingService> getPendingFirstServiceForSeletedMonthByDistrict(
			ReportFilter reportFilter) {
		Object param[] = getQueryParam(reportFilter);
		log.debug("---> param.length : " + param.length);
		String query = null;
		log.info("Param:     " + param[0]);
		log.debug("Param Array:     " + param);
		if (ReportConstants.DURATION_REPORT.equals(reportFilter.getFrom())
				&& !ReportConstants.NAVIGATE_LINK.equals(reportFilter
						.getNavLink())) {
			query = dao_constants.SELECT_SERVICE_PENDING_BYMONTH_BYDISTRICT_FIRST;
		} else {
			query = dao_constants.SELECT_SERVICE_PENDING_BYSELECTED_MONTH_DISTRICT_FIRST;
		}
		log.info("QUERY:" + query);
		return (List<PendingService>) jdbctemp.query(query, param,
				new RowMapper<PendingService>() {

					@Override
					public PendingService mapRow(ResultSet rs, int recCount)
							throws SQLException {
						// TODO Auto-generated method stub
						PendingService ps = new PendingService();
						ps.setChasisNumber(rs.getString("CHASISNUMBER"));
						ps.setActual_date_of_service(rs.getDate("SERVICE_DATE"));
						ps.setCustomerDetails((rs.getString("NAME_ADDR") + ","
								+ rs.getString("POST_OFFICE") + ","
								+ rs.getString("TALUK") + ","
								+ rs.getString("DISTRICT") + ","
								+ rs.getString("PINCODE") + "," + rs
								.getString("CONTACT_NO")).trim());
						ps.setServiceName(rs.getString("SERVICE_NAME"));
						return ps;
					}
				});
	}

	public List<PendingService> getPendingSecondServiceForSeletedMonthByDistrict(
			ReportFilter reportFilter) {
		Object param[] = getQueryParam(reportFilter);
		String query = null;
		if (ReportConstants.DURATION_REPORT.equals(reportFilter.getFrom())
				&& !ReportConstants.NAVIGATE_LINK.equals(reportFilter
						.getNavLink())) {
			query = dao_constants.SELECT_SERVICE_PENDING_BYMONTH_BYDISTRICT_SECOND;
		} else {
			query = dao_constants.SELECT_SERVICE_PENDING_BYSELECTED_MONTH_DISTRICT_SECOND;
		}
		return (List<PendingService>) jdbctemp.query(query, param,
				new RowMapper<PendingService>() {

					@Override
					public PendingService mapRow(ResultSet rs, int recCount)
							throws SQLException {
						// TODO Auto-generated method stub
						PendingService ps = new PendingService();
						ps.setChasisNumber(rs.getString("CHASISNUMBER"));
						ps.setActual_date_of_service(rs.getDate("SERVICE_DATE"));
						ps.setCustomerDetails((rs.getString("NAME_ADDR") + ","
								+ rs.getString("POST_OFFICE") + ","
								+ rs.getString("TALUK") + ","
								+ rs.getString("DISTRICT") + ","
								+ rs.getString("PINCODE") + "," + rs
								.getString("CONTACT_NO")).trim());
						ps.setServiceName(rs.getString("SERVICE_NAME"));
						return ps;
					}
				});
	}

	public List<PendingService> getPendingThirdServiceForSeletedMonthByDistrict(
			ReportFilter reportFilter) {
		Object param[] = getQueryParam(reportFilter);
		String query = null;
		if (ReportConstants.DURATION_REPORT.equals(reportFilter.getFrom())
				&& !ReportConstants.NAVIGATE_LINK.equals(reportFilter
						.getNavLink())) {
			query = dao_constants.SELECT_SERVICE_PENDING_BYMONTH_BYDISTRICT_THIRD;
		} else {
			query = dao_constants.SELECT_SERVICE_PENDING_BYSELECTED_MONTH_DISTRICT_THIRD;
		}
		log.debug("Query : " + query);
		log.debug("Parameters for query :" + param.toString() + " param :"
				+ param);
		return (List<PendingService>) jdbctemp.query(query, param,
				new RowMapper<PendingService>() {

					@Override
					public PendingService mapRow(ResultSet rs, int recCount)
							throws SQLException {
						// TODO Auto-generated method stub
						PendingService ps = new PendingService();
						ps.setChasisNumber(rs.getString("CHASISNUMBER"));
						ps.setActual_date_of_service(rs.getDate("SERVICE_DATE"));
						ps.setCustomerDetails((rs.getString("NAME_ADDR") + ","
								+ rs.getString("POST_OFFICE") + ","
								+ rs.getString("TALUK") + ","
								+ rs.getString("DISTRICT") + ","
								+ rs.getString("PINCODE") + "," + rs
								.getString("CONTACT_NO")).trim());
						ps.setServiceName(rs.getString("SERVICE_NAME"));
						return ps;
					}
				});
	}

	public List<PendingService> getPendingFourthServiceForSeletedMonthByDistrict(
			ReportFilter reportFilter) {
		Object param[] = getQueryParam(reportFilter);
		String query = null;
		if (ReportConstants.DURATION_REPORT.equals(reportFilter.getFrom())
				&& !ReportConstants.NAVIGATE_LINK.equals(reportFilter
						.getNavLink())) {
			query = dao_constants.SELECT_SERVICE_PENDING_BYMONTH_BYDISTRICT_FOURTH;
		} else {
			query = dao_constants.SELECT_SERVICE_PENDING_BYSELECTED_MONTH_DISTRICT_FOURTH;
		}
		return (List<PendingService>) jdbctemp.query(query, param,
				new RowMapper<PendingService>() {

					@Override
					public PendingService mapRow(ResultSet rs, int recCount)
							throws SQLException {
						// TODO Auto-generated method stub
						PendingService ps = new PendingService();
						ps.setChasisNumber(rs.getString("CHASISNUMBER"));
						ps.setActual_date_of_service(rs.getDate("SERVICE_DATE"));
						ps.setCustomerDetails((rs.getString("NAME_ADDR") + ","
								+ rs.getString("POST_OFFICE") + ","
								+ rs.getString("TALUK") + ","
								+ rs.getString("DISTRICT") + ","
								+ rs.getString("PINCODE") + "," + rs
								.getString("CONTACT_NO")).trim());
						ps.setServiceName(rs.getString("SERVICE_NAME"));
						return ps;
					}
				});
	}

	public List<PendingService> getPendingFifthServiceForSeletedMonthByDistrict(
			ReportFilter reportFilter) {
		Object param[] = getQueryParam(reportFilter);
		String query = null;
		if (ReportConstants.DURATION_REPORT.equals(reportFilter.getFrom())
				&& !ReportConstants.NAVIGATE_LINK.equals(reportFilter
						.getNavLink())) {
			query = dao_constants.SELECT_SERVICE_PENDING_BYMONTH_BYDISTRICT_FIFTH;
		} else {
			query = dao_constants.SELECT_SERVICE_PENDING_BYSELECTED_MONTH_DISTRICT_FIFTH;
		}
		return (List<PendingService>) jdbctemp.query(query, param,
				new RowMapper<PendingService>() {

					@Override
					public PendingService mapRow(ResultSet rs, int recCount)
							throws SQLException {
						// TODO Auto-generated method stub
						PendingService ps = new PendingService();
						ps.setChasisNumber(rs.getString("CHASISNUMBER"));
						ps.setActual_date_of_service(rs.getDate("SERVICE_DATE"));
						ps.setCustomerDetails((rs.getString("NAME_ADDR") + ","
								+ rs.getString("POST_OFFICE") + ","
								+ rs.getString("TALUK") + ","
								+ rs.getString("DISTRICT") + ","
								+ rs.getString("PINCODE") + "," + rs
								.getString("CONTACT_NO")).trim());
						ps.setServiceName(rs.getString("SERVICE_NAME"));
						return ps;
					}
				});
	}

	public List<PendingService> getPendingSixthServiceForSeletedMonthByDistrict(
			ReportFilter reportFilter) {
		Object param[] = getQueryParam(reportFilter);
		String query = null;
		if (ReportConstants.DURATION_REPORT.equals(reportFilter.getFrom())
				&& !ReportConstants.NAVIGATE_LINK.equals(reportFilter
						.getNavLink())) {
			query = dao_constants.SELECT_SERVICE_PENDING_BYMONTH_BYDISTRICT_SIXTH;
		} else {
			query = dao_constants.SELECT_SERVICE_PENDING_BYSELECTED_MONTH_DISTRICT_SIXTH;
		}
		return (List<PendingService>) jdbctemp.query(query, param,
				new RowMapper<PendingService>() {

					@Override
					public PendingService mapRow(ResultSet rs, int recCount)
							throws SQLException {
						// TODO Auto-generated method stub
						PendingService ps = new PendingService();
						ps.setChasisNumber(rs.getString("CHASISNUMBER"));
						ps.setActual_date_of_service(rs.getDate("SERVICE_DATE"));
						ps.setCustomerDetails((rs.getString("NAME_ADDR") + ","
								+ rs.getString("POST_OFFICE") + ","
								+ rs.getString("TALUK") + ","
								+ rs.getString("DISTRICT") + ","
								+ rs.getString("PINCODE") + "," + rs
								.getString("CONTACT_NO")).trim());
						ps.setServiceName(rs.getString("SERVICE_NAME"));
						return ps;
					}
				});
	}

	public List<PendingService> getPendingSeventhServiceForSeletedMonthByDistrict(
			ReportFilter reportFilter) {
		Object param[] = getQueryParam(reportFilter);
		String query = null;
		if (ReportConstants.DURATION_REPORT.equals(reportFilter.getFrom())
				&& !ReportConstants.NAVIGATE_LINK.equals(reportFilter
						.getNavLink())) {
			query = dao_constants.SELECT_SERVICE_PENDING_BYMONTH_BYDISTRICT_SEVENTH;
		} else {
			query = dao_constants.SELECT_SERVICE_PENDING_BYSELECTED_MONTH_DISTRICT_SEVENTH;
		}
		return (List<PendingService>) jdbctemp.query(query, param,
				new RowMapper<PendingService>() {

					@Override
					public PendingService mapRow(ResultSet rs, int recCount)
							throws SQLException {
						// TODO Auto-generated method stub
						PendingService ps = new PendingService();
						ps.setChasisNumber(rs.getString("CHASISNUMBER"));
						ps.setActual_date_of_service(rs.getDate("SERVICE_DATE"));
						ps.setCustomerDetails((rs.getString("NAME_ADDR") + ","
								+ rs.getString("POST_OFFICE") + ","
								+ rs.getString("TALUK") + ","
								+ rs.getString("DISTRICT") + ","
								+ rs.getString("PINCODE") + "," + rs
								.getString("CONTACT_NO")).trim());
						ps.setServiceName(rs.getString("SERVICE_NAME"));
						return ps;
					}
				});
	}

	public List<PendingService> getPendingEigthServiceForSeletedMonthByDistrict(
			ReportFilter reportFilter) {
		Object param[] = getQueryParam(reportFilter);
		String query = null;
		if (ReportConstants.DURATION_REPORT.equals(reportFilter.getFrom())
				&& !ReportConstants.NAVIGATE_LINK.equals(reportFilter
						.getNavLink())) {
			query = dao_constants.SELECT_SERVICE_PENDING_BYMONTH_BYDISTRICT_EIGTH;
		} else {
			query = dao_constants.SELECT_SERVICE_PENDING_BYSELECTED_MONTH_DISTRICT_EIGTH;
		}
		return (List<PendingService>) jdbctemp.query(query, param,
				new RowMapper<PendingService>() {

					@Override
					public PendingService mapRow(ResultSet rs, int recCount)
							throws SQLException {
						// TODO Auto-generated method stub
						PendingService ps = new PendingService();
						ps.setChasisNumber(rs.getString("CHASISNUMBER"));
						ps.setActual_date_of_service(rs.getDate("SERVICE_DATE"));
						ps.setCustomerDetails((rs.getString("NAME_ADDR") + ","
								+ rs.getString("POST_OFFICE") + ","
								+ rs.getString("TALUK") + ","
								+ rs.getString("DISTRICT") + ","
								+ rs.getString("PINCODE") + "," + rs
								.getString("CONTACT_NO")).trim());
						ps.setServiceName(rs.getString("SERVICE_NAME"));
						return ps;
					}
				});
	}

	@Override
	public String addSparesDetails(SparesDetails sparesDetails) {
		String message = "Failure";
		 
		try {
			System.out.println("Entered add Spares details !!!!");
			System.out.println("QUERY :" + dao_constants.INSERT_SPARES);

			int rowCount = jdbctemp.update(
					dao_constants.INSERT_SPARES,
 					new Object[] {
							sparesDetails.getSpareCode(),sparesDetails.getPartDescription(),sparesDetails.getPrice(),sparesDetails.getMinimumOrder()});

			System.out.println("SPARES added count : "+rowCount); 
			
			message = "success";
		} catch (DataIntegrityViolationException duplicateEntry) {
			message = "DuplicateEntry : " + duplicateEntry.getMessage();
			log.fatal("DuplicateEntry :: Exception while inserting Spares Data : "
					+ duplicateEntry);

		} catch (Exception exception) {
			// log.fatal("Exception while inserting Customer Data   : "+exception.printStackTrace());
			exception.printStackTrace();
			// System.out.println();
		}
		return message;
	}
	@Override	
	public Map<String, Object> getConsolidatedSalaryDetailsByYear(String fetchYear){
		List<SalaryDetails> salaryDetails = new ArrayList<SalaryDetails>();
		Map<String, Object> datas = new HashMap<String, Object>();
		try{
			System.out.println("Entered Consolidated SalaryDetails By Year !!!!");
			System.out.println("QUERY :" + dao_constants.SELECT_COMPLETE_SALARYDETAILS_SALARY_YEAR);
			
			salaryDetails = (List<SalaryDetails>)jdbctemp.query(dao_constants.SELECT_COMPLETE_SALARYDETAILS_SALARY_YEAR, new Object[]{fetchYear}, new RowMapper<SalaryDetails>() {

				@Override
				public SalaryDetails mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					SalaryDetails sd = new  SalaryDetails();
					sd.setAsOnDate(ft.format(rs.getDate("asondate")));
					sd.seteId(rs.getString("eid"));
					sd.setSalary(rs.getDouble("salary"));
					sd.setEmployeeName(rs.getString("Employee_Name"));
					return sd;
				}
			});			
			datas.put("dataList", salaryDetails);
		}catch (org.springframework.dao.EmptyResultDataAccessException ex) {
			ex.printStackTrace();
			datas.put("dataList", salaryDetails);
			datas.put("message", "No Records Found");
		}catch (Exception ex) {
			log.fatal("Exception Fetching Data : " , ex);
			System.out.println("Exception Fetching Data : "+ex);
			datas.put("dataList", salaryDetails);
			datas.put("message", "Exception Fetching Data : "+ex);
		}
	return datas;
	}
	@Override		
	public List<Object> getSalaryDetailsYears(){
		List<Object> listOfYears = new ArrayList<Object>();
		try{
			listOfYears = jdbctemp.queryForList(dao_constants.SELECT_CR_SALARYDETAILS_SALARY_YEAR, Object.class);
		}catch (org.springframework.dao.EmptyResultDataAccessException ex) {
			ex.printStackTrace();
		}catch (Exception exception) {
			// log.fatal("Exception while inserting Customer Data   : "+exception.printStackTrace());
			exception.printStackTrace();
			// System.out.println();
		}
		return listOfYears;
	}
	@Override
	public String addFeedbackDetails(FeedbackDetails feedbackDetails) {
		String message = "Failure";
		 // `servicestarttime`,`serviceendtime`,`explanationofproblem`,`explanationoftractor,
		//`explanationofimplement`,`customerothercomplaint`,`technitianreplyforproblem`,`posttroubleshoottest`,`sysgeneratedrating`;
		// YYYY-MM-DD HH:MM:SS
		// STR_TO_DATE('5/15/2012 8:06:26 AM', '%c/%e/%Y %r')
		try {
			System.out.println("Entered add Feedback details !!!!");
			System.out.println("QUERY :" + dao_constants.INSERT_FEEDBACK);
/*
 
 `qualityofwork`,`cleaningofvehicle`,`timelycompletion`,`overallrating`,`remark`,`jobcardnumber`,`chasisnumber`,`eid`,
 `dateoffeedback`,`issuestatus`,`servicestarttime`,`serviceendtime`,`explanationofproblem`,`explanationoftractor,
 `explanationofimplement`,`customerothercomplaint`,`technitianreplyforproblem`,`posttroubleshoottest`,
 `sysgeneratedrating`,`totaltimetaken`,`otherproblemdetails`,`techniciansolution`
 
 */
			
			int rowCount = jdbctemp.update(
					dao_constants.INSERT_FEEDBACK,
 					new Object[] {
							feedbackDetails.getQualityOfWork(),
							feedbackDetails.getCleaningOfVehicle(),
							feedbackDetails.getTimelyCompletion(),
							feedbackDetails.getOverallRating(),
							feedbackDetails.getRemark(),
							feedbackDetails.getJobcardNumber(),
							feedbackDetails.getChasisNumber(),
							feedbackDetails.getEid(),
							feedbackDetails.getDateOfFeedback(),
							feedbackDetails.getIssueStatus(),
							feedbackDetails.getServicestarttime(),
							feedbackDetails.getServiceendtime(),
							feedbackDetails.getExplanationofproblem(),
							feedbackDetails.getExplanationoftractor(),
							feedbackDetails.getExplanationofimplement(), 
							feedbackDetails.getTechnitianreplyforproblem(),
							feedbackDetails.getPosttroubleshoottest(),
							getSystemGenScore(feedbackDetails),
							feedbackDetails.getTimeSpentByEngineer(),
							feedbackDetails.getOtherProblemDetails(),
							feedbackDetails.getTechnicianSolution() 
						});
			System.out.println("SPARES added count : "+rowCount); 
			
			message = "success";
		} catch (DataIntegrityViolationException duplicateEntry) {
			message = "DuplicateEntry : " + duplicateEntry.getMessage();
			log.fatal("DuplicateEntry :: Exception while inserting Feedback Data : "
					+ duplicateEntry);

		} catch (Exception exception) {
			// log.fatal("Exception while inserting Customer Data   : "+exception.printStackTrace());
			exception.printStackTrace();
			// System.out.println();
		}
		return message;
	}
	
	private int getSystemGenScore(final FeedbackDetails feedbackDetails){
		return 5;
	}
	@Override
	public String updateSparesDetails(SparesDetails sparesDetails) {
		String message = "Failure";
		 
		try {
			System.out.println("Entered Update Spares details !!!!");
			System.out.println("QUERY :" + dao_constants.UPDATE_SPARES);

			int rowCount = jdbctemp.update(
					dao_constants.UPDATE_SPARES,
 					new Object[] {
							sparesDetails.getSpareCode(),sparesDetails.getPartDescription(),sparesDetails.getPrice(),sparesDetails.getMinimumOrder(),sparesDetails.getSpareCode()});

			System.out.println("SPARES Updated count : "+rowCount); 
			
			message = "success";
		} catch (DataIntegrityViolationException duplicateEntry) {
			message = "DuplicateEntry : " + duplicateEntry.getMessage();
			log.fatal("DuplicateEntry :: Exception while updating Spares Data : "
					+ duplicateEntry);

		} catch (Exception exception) {
			// log.fatal("Exception while inserting Customer Data   : "+exception.printStackTrace());
			exception.printStackTrace();
			// System.out.println();
		}
		return message;
	} 
	@Override
	public String updateFeedbackDetails(FeedbackDetails feedbackDetails) {
		String message = "Failure";
		try {
			System.out.println("Entered Update Spares details !!!!");
			System.out.println("QUERY :" + dao_constants.UPDATE_FEEDBACK);

			int rowCount = jdbctemp.update(
					dao_constants.UPDATE_FEEDBACK,
 					new Object[] {feedbackDetails.getQualityOfWork(),
							feedbackDetails.getCleaningOfVehicle(),
							feedbackDetails.getTimelyCompletion(),
							feedbackDetails.getOverallRating(),
							feedbackDetails.getRemark(),
							feedbackDetails.getJobcardNumber(),
							feedbackDetails.getChasisNumber(),
							feedbackDetails.getDateOfFeedback(),
							feedbackDetails.getEid(),
							feedbackDetails.getIssueStatus(),
							feedbackDetails.getServicestarttime(),
							feedbackDetails.getServiceendtime(),
							feedbackDetails.getExplanationofproblem(),
							feedbackDetails.getExplanationoftractor(),
							feedbackDetails.getExplanationofimplement(),
							feedbackDetails.getOtherProblemDetails(),
							feedbackDetails.getTechnitianreplyforproblem(),
							feedbackDetails.getTechnicianSolution(),
							feedbackDetails.getPosttroubleshoottest(),
							getSystemGenScore(feedbackDetails),							
							feedbackDetails.getTimeSpentByEngineer(),
							feedbackDetails.getFeedbackId(),
							
					});

			System.out.println("FEEDBACK Updated count : "+rowCount); 
			
			message = "success";
		} catch (DataIntegrityViolationException duplicateEntry) {
			message = "DuplicateEntry : " + duplicateEntry.getMessage();
			log.fatal("DuplicateEntry :: Exception while updating FEEDBACK Data : "
					+ duplicateEntry);

		} catch (Exception exception) {
			// log.fatal("Exception while inserting Customer Data   : "+exception.printStackTrace());
			exception.printStackTrace();
			// System.out.println();
		}
		return message;
	} 
	@Override
	public SparesDetails getReportForParticularSparesCode(String sparesCode,
			final SparesDetails sparesDetails) {
		if (jdbctemp != null) {
			try{
				System.out.println("Fetch Spares Details Query : "+dao_constants.SELECT_SPARE_DETAILS);
				jdbctemp.queryForObject(dao_constants.SELECT_SPARE_DETAILS, new Object[]{sparesCode},new RowMapper<SparesDetails>() {

					@Override
					public SparesDetails mapRow(ResultSet rs, int count)
							throws SQLException {
						sparesDetails.setSpareCode(rs.getString("spare_code"));
						sparesDetails.setPartDescription(rs.getString("part_description"));
						sparesDetails.setPrice(rs.getDouble("cost"));
						sparesDetails.setObsolete(rs.getString("obsolete"));
						sparesDetails.setMinimumOrder(rs.getInt("min_order"));
						return sparesDetails;
					}
					
				});
				sparesDetails.setMessage("success");
				
			}catch (org.springframework.dao.EmptyResultDataAccessException ex) {
				sparesDetails.setMessage("No Records Found");
			} catch (Exception ex) {
				log.fatal("Exception Fetching Data : " + ex);
			}
		}
		return sparesDetails;
	}
	@Override
	public Map<String, Object> getAssociateLeaveDetails(String queryName,final String diffCheck,final Integer year,final Integer month) {
		List<LeaveDetails> associateLeaveDetails = new ArrayList<LeaveDetails>();
		Map<String, Object> datas = new HashMap<String, Object>();
		try{
			List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
		if (jdbctemp != null) {
			rows = jdbctemp.queryForList(dao_constants.SELECT_SALARYDETAILS_BY_ASSOC_YEAR,new Object[]{queryName,year});
			for (Map row : rows) {
				LeaveDetails leaveDetail = new LeaveDetails();
				if(row.get("eid")==null){
					leaveDetail.setEid("NA");
				}else{
					leaveDetail.setEid(((String)row.get("eid")).trim());
				}
				if(row.get("Employee_Name")==null){
					leaveDetail.setEid("NA");
				}else{
					leaveDetail.setAssociateName(((String)row.get("Employee_Name")).trim());
				}
				if(row.get("noofdaysworked")==null){
					leaveDetail.setNoOfDaysWorked(0);
				}else{
					leaveDetail.setNoOfDaysWorked(((Integer)row.get("noofdaysworked")));
				}
				if(row.get("noofleaves")==null){
					leaveDetail.setNoOfLeaves(0);
				}else{
					leaveDetail.setNoOfLeaves(((Integer)row.get("noofleaves")));
				}
				if(row.get("noofdaysinmonth")==null){
					leaveDetail.setTotalNoOfDays(0);
				}else{
					leaveDetail.setTotalNoOfDays(((Integer)row.get("noofdaysinmonth")));
				}
				if(row.get("noofworkingdays")==null){
					leaveDetail.setNoOfWorkingDays(0);
				}else{
					leaveDetail.setNoOfWorkingDays(((Integer)row.get("noofworkingdays")));
				}

				if(row.get("salarydate")==null){
					leaveDetail.setCalendarDetails("NA");
				}else{
					leaveDetail.setCalendarDetails(ft.format((Date)row.get("salarydate")));
				} 
				associateLeaveDetails.add(leaveDetail);
			}
		}	
		datas.put("dataList", associateLeaveDetails);
		}catch (org.springframework.dao.EmptyResultDataAccessException ex) {
			//sparesDetails.setMessage("No Records Found");
			datas.put("dataList", associateLeaveDetails);
			datas.put("message", "No Records Found"); 
		}catch(Exception ex){
			log.fatal("Exception Fetching Data : " , ex);
			System.out.println("Exception Fetching Data : "+ex);
			datas.put("dataList", associateLeaveDetails);
			datas.put("message", "Exception Fetching Data : "+ex);
		}
		return datas;
	}	
	@Override
	public Map<String, Object> getAssociateSalaryDetails(String queryName,final String diffCheck,final Integer year,final Integer month) {
		List<AssociateSalaryDetails> associateSalaryDetails = new ArrayList<AssociateSalaryDetails>();
		Map<String, Object> datas = new HashMap<String, Object>();
		try{
		if (jdbctemp != null) {
			List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
			
			if(year==0 && "---".equals(queryName) && month==0)
			{
				System.out.println("Fetch Salary Details For All Month year: "+dao_constants.SELECT_SALARYDETAILS_ALL);
				rows = jdbctemp.queryForList(dao_constants.SELECT_SALARYDETAILS_ALL);
			}else if(year==0 && !"---".equals(queryName) && month==0){
				System.out.println("Fetch Salary Details by Assoc Query : "+dao_constants.SELECT_SALARYDETAILS_BY_ASSOC);
				rows = jdbctemp.queryForList(dao_constants.SELECT_SALARYDETAILS_BY_ASSOC, new Object[]{queryName});
			}else if(year>0 && "---".equals(queryName) && month==0){
				System.out.println("Fetch Salary Details by year Query : "+dao_constants.SELECT_SALARYDETAILS_BY_YEAR);
				rows = jdbctemp.queryForList(dao_constants.SELECT_SALARYDETAILS_BY_YEAR, new Object[]{year});
			}else if(year>0 && "---".equals(queryName) && month>0){
				System.out.println("Fetch Salary Details by year month Query : "+dao_constants.SELECT_SALARYDETAILS_BY_YEAR_MONTH);
				rows = jdbctemp.queryForList(dao_constants.SELECT_SALARYDETAILS_BY_YEAR_MONTH, new Object[]{year,month});
			}else if(year==0 && "---".equals(queryName) && month>0){
				System.out.println("Fetch Salary Details by month Query : "+dao_constants.SELECT_SALARYDETAILS_BY_MONTH);
				rows = jdbctemp.queryForList(dao_constants.SELECT_SALARYDETAILS_BY_MONTH, new Object[]{month});
			}else if(year==0 && !"---".equals(queryName) && month>0){
				System.out.println("Fetch Salary Details by month assoc Query : "+dao_constants.SELECT_SALARYDETAILS_BY_MONTH_ASSOC);
				rows = jdbctemp.queryForList(dao_constants.SELECT_SALARYDETAILS_BY_MONTH_ASSOC, new Object[]{month,queryName});
			}else if(year>0 && !"---".equals(queryName) && month>0){
				System.out.println("Fetch Salary Details by year mont assoc Query : "+dao_constants.SELECT_SALARYDETAILS_BY_MONTH_YEAR_ASSOC);
				rows = jdbctemp.queryForList(dao_constants.SELECT_SALARYDETAILS_BY_MONTH_YEAR_ASSOC, new Object[]{year,month,queryName});
			}else if(year>0 && !"---".equals(queryName) && month==0){
				System.out.println("Fetch Salary Details by year assoc Query : "+dao_constants.SELECT_SALARYDETAILS_BY_YEAR_ASSOC);
				rows = jdbctemp.queryForList(dao_constants.SELECT_SALARYDETAILS_BY_YEAR_ASSOC, new Object[]{year,queryName});
			}
			else{
				System.out.println("Fetch Salary Details for Current : "+dao_constants.SELECT_SALARYDETAILS_BY_CURRENT_MONTH);
				rows = jdbctemp.queryForList(dao_constants.SELECT_SALARYDETAILS_BY_CURRENT_MONTH);
			}	
			
			for (Map row : rows) {
				AssociateSalaryDetails asd = new AssociateSalaryDetails();
				if(row.get("eid")==null){
					asd.setEid("NA");
				}else{
					asd.setEid(((String)row.get("eid")).trim());
				}
				if(row.get("basesalary")==null){
					asd.setBaseSalary(0.0);
				}else{
					asd.setBaseSalary(((Double)row.get("basesalary")));
				}
				if(row.get("noofdaysworked")==null){
					asd.setNoOfDaysWorked(0);
				}else{
					asd.setNoOfDaysWorked(((Integer)row.get("noofdaysworked")));
				}
				if(row.get("dailyallowance")==null){
					asd.setDailyAllowance(0.0);
				}else{
					asd.setDailyAllowance(((Double)row.get("dailyallowance")));
				}
				if(row.get("totalsalary")==null){
					asd.setTotalSalary(0.0);
				}else{
					asd.setTotalSalary(((Double)row.get("totalsalary")));
				}
				if(row.get("noofleaves")==null){
					asd.setLeaveCount(0);
				}else{
					asd.setLeaveCount(((Integer)row.get("noofleaves")));
				}
				if(row.get("Employee_Name")==null){
					asd.setAssocName("NA");
				}else{
					asd.setAssocName(((String)row.get("Employee_Name")));
				}

				if(row.get("salarydate")==null){
					asd.setSalaryDate("NA");
				}else{
					
					asd.setSalaryDate(ft.format((Date)row.get("salarydate")));
				}
				
				List<AssociateDaDetails> daDetails = getDaStringForAssociate(((String)row.get("eid")).trim(),ft.format((Date)row.get("salarydate")));
				StringBuilder daDetailsString = new StringBuilder();
				for(int i=0;i<daDetails.size();i++){
					if(i==(daDetails.size()-1)){
						daDetailsString.append("DA : "+daDetails.get(i).getDaperday()+" * "+daDetails.get(i).getNoofdays()+" = "+daDetails.get(i).getAmount());
					}else{
						daDetailsString.append("DA : "+daDetails.get(i).getDaperday()+" * "+daDetails.get(i).getNoofdays()+" = "+daDetails.get(i).getAmount()+" || ");
					}
				}
				//asd.setAssociateDaDetails(daDetails);
				asd.setDaDetailsString(daDetailsString.toString());
				associateSalaryDetails.add(asd);
			}	
			
		}
	/*	if(associateSalaryDetails.size()>0)
		{
			datas.put("dataList", associateSalaryDetails);
		}else
		{
			datas.put("dataList", associateSalaryDetails);
		}
	*/	
		datas.put("dataList", associateSalaryDetails);
		}catch (org.springframework.dao.EmptyResultDataAccessException ex) {
			//sparesDetails.setMessage("No Records Found");
			datas.put("dataList", associateSalaryDetails);
			datas.put("message", "No Records Found"); 
		}catch(Exception ex){
			log.fatal("Exception Fetching Data : " , ex);
			System.out.println("Exception Fetching Data : "+ex);
			datas.put("dataList", associateSalaryDetails);
			datas.put("message", "Exception Fetching Data : "+ex);
		}
		return datas;
	}	
@Override	
	public List<AssociateDaDetails> getDaStringForAssociate(String associateId,
			String salaryDate) {
		List<AssociateDaDetails> daDetails = new ArrayList<AssociateDaDetails>();
		try {
			List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
			if (jdbctemp != null) {
				rows = jdbctemp
						.queryForList(dao_constants.SELECT_DADETAILS_FOR_ASSOC_SAL_DATE,new Object[]{associateId,salaryDate});
				for (Map row : rows) {
					AssociateDaDetails assocDaDetails = new AssociateDaDetails();
					if (row.get("datype") == null) {
						assocDaDetails.setDatype("NA");
					} else {
						assocDaDetails.setDatype(((String) row.get("datype")).trim());
					}
					if (row.get("gendate") == null) {
						assocDaDetails.setGendate("NA");
					} else {
						assocDaDetails.setGendate(ft.format((Date)row.get("gendate")));
					}
					if (row.get("noofdays") == null) {
						assocDaDetails.setNoofdays(0);
					} else {
						assocDaDetails.setNoofdays(((Integer) row.get("noofdays")));
					}
					if (row.get("amount") == null) {
						assocDaDetails.setAmount(0.0);
					} else {
						assocDaDetails.setAmount(((Double) row.get("amount")));
					}
					if (row.get("daperday") == null) {
						assocDaDetails.setDaperday(0.0);
					} else {
						assocDaDetails.setDaperday(((Double) row.get("daperday")));
					}
					daDetails.add(assocDaDetails);
				}

			}
		} catch (org.springframework.dao.EmptyResultDataAccessException ex) {
			System.out.println("Exception Fetching Data : "+ex);
			ex.printStackTrace();
		}catch(Exception ex){
			log.fatal("Exception Fetching Data : " , ex);
			System.out.println("Exception Fetching Data : "+ex);
			ex.printStackTrace();
		}
		return daDetails;
	}	
	
	@Override
	public Map<String, Object> getCustomerDetails(String queryName,final String diffCheck,final Integer year,final Integer month) {
		List<CustomerDetails> customerDetails = new ArrayList<CustomerDetails>();
		Map<String, Object> datas = new HashMap<String, Object>();
		if (jdbctemp != null) {
			
		if("TALUK".equals(diffCheck)){
			try{
				List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
				if(year==0 && "---".equals(queryName) && month==0)
				{
					System.out.println("Fetch CUSTOMER Details For All : "+dao_constants.SELECT_CUTOMER_ALL);
					rows = jdbctemp.queryForList(dao_constants.SELECT_CUTOMER_ALL);
				}else if(year==0 && !"---".equals(queryName) && month==0){
					System.out.println("Fetch CUSTOMER Details by Taluk Query : "+dao_constants.SELECT_CUTOMER_BY_TALUK);
					rows = jdbctemp.queryForList(dao_constants.SELECT_CUTOMER_BY_TALUK, new Object[]{queryName});
				}else if(year>0 && "---".equals(queryName) && month==0){
					System.out.println("Fetch CUSTOMER Details by year Query : "+dao_constants.SELECT_CUTOMER_BY_YEAR);
					rows = jdbctemp.queryForList(dao_constants.SELECT_CUTOMER_BY_YEAR, new Object[]{year});
				}else if(year>0 && "---".equals(queryName) && month>0){
					System.out.println("Fetch CUSTOMER Details by year Query : "+dao_constants.SELECT_CUTOMER_BY_YEAR_MONTH);
					rows = jdbctemp.queryForList(dao_constants.SELECT_CUTOMER_BY_YEAR_MONTH, new Object[]{year,month});
				}else if(year==0 && "---".equals(queryName) && month>0){
					System.out.println("Fetch CUSTOMER Details by year Query : "+dao_constants.SELECT_CUTOMER_BY_MONTH);
					rows = jdbctemp.queryForList(dao_constants.SELECT_CUTOMER_BY_MONTH, new Object[]{month});
				}else if(year==0 && !"---".equals(queryName) && month>0){
					System.out.println("Fetch CUSTOMER Details by year Query : "+dao_constants.SELECT_CUTOMER_BY_MONTH_TALUK);
					rows = jdbctemp.queryForList(dao_constants.SELECT_CUTOMER_BY_MONTH_TALUK, new Object[]{queryName,month});
				}else if(year>0 && !"---".equals(queryName) && month>0){
					System.out.println("Fetch CUSTOMER Details by year Query : "+dao_constants.SELECT_CUTOMER_BY_MONTH_YEAR_TALUK);
					rows = jdbctemp.queryForList(dao_constants.SELECT_CUTOMER_BY_MONTH_YEAR_TALUK, new Object[]{queryName,year,month});
				}else{
					System.out.println("Fetch CUSTOMER Details for all : "+dao_constants.SELECT_CUTOMER_BY_TALUK_YEAR);
					rows = jdbctemp.queryForList(dao_constants.SELECT_CUTOMER_BY_TALUK_YEAR, new Object[]{queryName,year});
				}
				
				
				for (Map row : rows) {
					CustomerDetails cd = new CustomerDetails();
					if(row.get("chasisNumber")==null){
						cd.setChasisNumber("NA");
					}else{
						cd.setChasisNumber(((String)row.get("chasisNumber")).trim());
					}
					if(row.get("contact_no")==null){
						cd.setContactNumber("NA");
					}else
					{
						cd.setContactNumber(((String)row.get("contact_no")).trim());
					}
					if(row.get("name_address")==null){
						cd.setCustomerDetails("NA");
					}else
					{
						cd.setCustomerDetails(((String)row.get("name_address")).trim());
					}
					if(row.get("customerId")==null){
						cd.setCustomerId(0);
					}else{
						cd.setCustomerId((Integer)row.get("customerId"));	
					}
					if(row.get("dateOfSale")==null){
						cd.setDateOFsale("NA");
					}else{
						cd.setDateOFsale(((String)row.get("dateOfSale")).trim());
					}
					
					if(row.get("delivaryBranch")==null){
						cd.setDelivaryBranch("");
					}else
					{
						cd.setDelivaryBranch(((String)row.get("delivaryBranch")).trim());
					}
					if(row.get("district")==null){
						cd.setDistrict("NA");
					}else{
						cd.setDistrict(((String)row.get("district")).trim());
					}
					if(row.get("engineNumber")==null){
						cd.setEngineNumber("NA");
					}else{
						cd.setEngineNumber(((String)row.get("engineNumber")).trim());
					}
					if(row.get("installedDate")==null){
						cd.setInstalledDate("NA");
					}else{
						cd.setInstalledDate(((String)row.get("installedDate")).trim());
					}
					if(row.get("name_address")==null){
						cd.setNameaddr("NA");
					}else{
						cd.setNameaddr(((String)row.get("name_address")).trim());
					}
					if(row.get("Post Office")==null){
						cd.setPostOffice("NA");
					}else{
						cd.setPostOffice(((String)row.get("Post Office")).trim());
					}
					if(row.get("Taluk")==null){
						cd.setTaluk("NA");
					}else{
						cd.setTaluk(((String)row.get("Taluk")).trim());
					}
					if(row.get("pincode")==null){
						cd.setPincode("NA");
					}else{
						cd.setPincode(((String)row.get("pincode")).trim());
					} 
					
					customerDetails.add(cd); 
				}
				
			}catch (org.springframework.dao.EmptyResultDataAccessException ex) {
				//sparesDetails.setMessage("No Records Found");
				datas.put("dataList", customerDetails);
				datas.put("message", "No Records Found"); 
			}catch(Exception ex){
				log.fatal("Exception Fetching Data : " , ex);
				System.out.println("Exception Fetching Data : "+ex);
			}
			
		}else if("DISTRICT".equals(diffCheck)){
			try{
				
				List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
				if(year==0 && "---".equals(queryName) && month==0)
				{
					System.out.println("Fetch CUSTOMER Details by District ALL: "+dao_constants.SELECT_DT_CUTOMER_BY_ALL);
					rows = jdbctemp.queryForList(dao_constants.SELECT_DT_CUTOMER_BY_ALL);
				}else if(year==0 && !"---".equals(queryName) && month==0){
					System.out.println("Fetch CUSTOMER Details by District Query : "+dao_constants.SELECT_DT_CUTOMER_BY_DISTRICT);
					rows = jdbctemp.queryForList(dao_constants.SELECT_DT_CUTOMER_BY_DISTRICT, new Object[]{queryName});
				}else if(year>0 && "---".equals(queryName) && month==0){
					System.out.println("Fetch CUSTOMER Details by District year Query : "+dao_constants.SELECT_DT_CUTOMER_BY_YEAR);
					rows = jdbctemp.queryForList(dao_constants.SELECT_DT_CUTOMER_BY_YEAR, new Object[]{year});
				}else if(year>0 && "---".equals(queryName) && month>0){
					System.out.println("Fetch CUSTOMER Details by year Query : "+dao_constants.SELECT_CUTOMER_BY_YEAR_MONTH);
					rows = jdbctemp.queryForList(dao_constants.SELECT_CUTOMER_BY_YEAR_MONTH, new Object[]{year,month});
				}else if(year==0 && "---".equals(queryName) && month>0){
					System.out.println("Fetch CUSTOMER Details by year Query : "+dao_constants.SELECT_CUTOMER_BY_MONTH);
					rows = jdbctemp.queryForList(dao_constants.SELECT_CUTOMER_BY_MONTH, new Object[]{month});
				}else if(year==0 && !"---".equals(queryName) && month>0){
					System.out.println("Fetch CUSTOMER Details by year Query : "+dao_constants.SELECT_CUTOMER_BY_MONTH_DISTRICT);
					rows = jdbctemp.queryForList(dao_constants.SELECT_CUTOMER_BY_MONTH_DISTRICT, new Object[]{queryName,month});
				}else if(year>0 && !"---".equals(queryName) && month>0){
					System.out.println("Fetch CUSTOMER Details by year Query : "+dao_constants.SELECT_CUTOMER_BY_MONTH_YEAR_DISTRICT);
					rows = jdbctemp.queryForList(dao_constants.SELECT_CUTOMER_BY_MONTH_YEAR_DISTRICT, new Object[]{queryName,year,month});
				}else{
					System.out.println("Fetch CUSTOMER Details by District year Query : "+dao_constants.SELECT_DT_CUTOMER_BY_DISTRICT_YEAR);
					rows = jdbctemp.queryForList(dao_constants.SELECT_DT_CUTOMER_BY_DISTRICT_YEAR, new Object[]{queryName,year});
				}
				 
				for (Map row : rows) {CustomerDetails cd = new CustomerDetails();
				if(row.get("chasisNumber")==null){
					cd.setChasisNumber("NA");
				}else{
					cd.setChasisNumber(((String)row.get("chasisNumber")).trim());
				}
				if(row.get("contact_no")==null){
					cd.setContactNumber("NA");
				}else
				{
					cd.setContactNumber(((String)row.get("contact_no")).trim());
				}
				if(row.get("name_address")==null){
					cd.setCustomerDetails("NA");
				}else
				{
					cd.setCustomerDetails(((String)row.get("name_address")).trim());
				}
				if(row.get("customerId")==null){
					cd.setCustomerId(0);
				}else{
					cd.setCustomerId((Integer)row.get("customerId"));	
				}
				if(row.get("dateOfSale")==null){
					cd.setDateOFsale("NA");
				}else{
					cd.setDateOFsale(((String)row.get("dateOfSale")).trim());
				}
				
				if(row.get("delivaryBranch")==null){
					cd.setDelivaryBranch("");
				}else
				{
					cd.setDelivaryBranch(((String)row.get("delivaryBranch")).trim());
				}
				if(row.get("district")==null){
					cd.setDistrict("NA");
				}else{
					cd.setDistrict(((String)row.get("district")).trim());
				}
				if(row.get("engineNumber")==null){
					cd.setEngineNumber("NA");
				}else{
					cd.setEngineNumber(((String)row.get("engineNumber")).trim());
				}
				if(row.get("installedDate")==null){
					cd.setInstalledDate("NA");
				}else{
					cd.setInstalledDate(((String)row.get("installedDate")).trim());
				}
				if(row.get("name_address")==null){
					cd.setNameaddr("NA");
				}else{
					cd.setNameaddr(((String)row.get("name_address")).trim());
				}
				if(row.get("Post Office")==null){
					cd.setPostOffice("NA");
				}else{
					cd.setPostOffice(((String)row.get("Post Office")).trim());
				}
				if(row.get("Taluk")==null){
					cd.setTaluk("NA");
				}else{
					cd.setTaluk(((String)row.get("Taluk")).trim());
				}
				if(row.get("pincode")==null){
					cd.setPincode("NA");
				}else{
					cd.setPincode(((String)row.get("pincode")).trim());
				} 
				
				customerDetails.add(cd); 
				}
			}catch (org.springframework.dao.EmptyResultDataAccessException ex) {
				//sparesDetails.setMessage("No Records Found");
				datas.put("dataList", customerDetails);
				datas.put("message", "No Records Found"); 
			}catch(Exception ex){
				log.fatal("Exception Fetching Data : " , ex);
				System.out.println("Exception Fetching Data : "+ex);
			}
			
		}
		if(customerDetails.size()>0)
		{
			datas.put("dataList", customerDetails);
		}else
		{
			datas.put("dataList", customerDetails);
		}
		
		datas.put("message", "success"); 
	}
		return datas;
	}	
	@Override
	public Map<String, Object> getFeedbackForParticularJobCard(String jobCardNumber,
			final FeedbackDetails feedbackDetail,final String uiCheck) {
		Map<String, Object> datas = new HashMap<String, Object>();
		List<FeedbackDetails> feedbackDetails = new ArrayList<FeedbackDetails>();
		List<FeedbackUiDetails> feedbackUIDetails = new ArrayList<FeedbackUiDetails>();
		if (jdbctemp != null) {
			try{
				System.out.println("Fetch Feedback Details Query : "+dao_constants.SELECT_FEEDBACK_DETAILS);
				
				List<Map<String, Object>> rows = jdbctemp.queryForList(dao_constants.SELECT_FEEDBACK_DETAILS, new Object[]{jobCardNumber});
				//<a href="jobCardReport.do?navLink=leftPane&from=updateJobCardReport">Update job card Detail</a>
				for (Map row : rows) {
					if("UI".equalsIgnoreCase(uiCheck)){
						FeedbackUiDetails fb = new FeedbackUiDetails();
						int id = (Integer)row.get("feedbackid");
						fb.setFeedbackId("<a href=\"fetchFeedbackDetail.do?navLink=leftPane&from=retrieveFeedbackReport&feedbackid="+id+"\">"+id+"</a>");
						fb.setQualityOfWork((Integer)row.get("qualityofwork"));
						fb.setCleaningOfVehicle((Integer)row.get("cleaningofvehicle"));
						fb.setTimelyCompletion((Integer)row.get("timelycompletion"));
						fb.setOverallRating((Integer)row.get("overallrating"));
						fb.setRemark((String)row.get("remark"));
						fb.setJobcardNumber((String)row.get("jobcardnumber"));
						fb.setChasisNumber((String)row.get("chasisnumber"));
						fb.setEid((String)row.get("eid"));
						fb.setIssueStatus((String)row.get("issuestatus"));
						fb.setDateOfFeedback((String)row.get("dateoffeedback"));
						feedbackUIDetails.add(fb);	
					}else
					{ 
						FeedbackDetails fb = new FeedbackDetails();
						fb.setFeedbackId((Integer)(row.get("feedbackid")));
						fb.setQualityOfWork((Integer)row.get("qualityofwork"));
						fb.setCleaningOfVehicle((Integer)row.get("cleaningofvehicle"));
						fb.setTimelyCompletion((Integer)row.get("timelycompletion"));
						fb.setOverallRating((Integer)row.get("overallrating"));
						fb.setRemark((String)row.get("remark"));
						fb.setJobcardNumber((String)row.get("jobcardnumber"));
						fb.setChasisNumber((String)row.get("chasisnumber"));
						fb.setEid((String)row.get("eid"));
						fb.setIssueStatus((String)row.get("issuestatus"));
						fb.setDateOfFeedback((String)row.get("dateoffeedback"));
						feedbackDetails.add(fb);
					}
					
				}
				if(feedbackUIDetails.size()>0)
				{
					datas.put("dataList", feedbackUIDetails);
				}else
				{
					datas.put("dataList", feedbackDetails);
				}
				
				datas.put("message", "success"); 
				
			}catch (org.springframework.dao.EmptyResultDataAccessException ex) {
				//sparesDetails.setMessage("No Records Found");
				datas.put("dataList", feedbackDetails);
				datas.put("message", "No Records Found"); 
			} catch (Exception ex) {
				log.fatal("Exception Fetching Data : " + ex);
			}
		}
		return datas;
	}
	@Override
	public EmployeeDetails getReportForParticularAssocId(String sparesCode,
			final EmployeeDetails assocDetails) {
		if (jdbctemp != null) {
			try{
				System.out.println("Fetch Spares Details Query : "+dao_constants.SELECT_ASSOC_DETAILS);
				jdbctemp.queryForObject(dao_constants.SELECT_ASSOC_DETAILS, new Object[]{sparesCode},new RowMapper<EmployeeDetails>() {

					@Override
					public EmployeeDetails mapRow(ResultSet rs, int count)
							throws SQLException { 
						assocDetails.setAddress(rs.getString("Address"));
						assocDetails.setDateOfJoin(dateUtil.getStringfromDate(rs.getDate("Date_of_join")));
						assocDetails.setDepartment(rs.getString("Department"));
						assocDetails.setDesignation(rs.getString("Designation"));
						assocDetails.setEmployeeId(rs.getString("EID"));
						assocDetails.setEmployeeName(rs.getString("Employee_Name"));
						assocDetails.setMobileNo(rs.getString("Mobile"));
						return assocDetails;
					}
					
				});
				assocDetails.setMessage("success");
				
			}catch (org.springframework.dao.EmptyResultDataAccessException ex) {
				assocDetails.setMessage("No Records Found");
			} catch (Exception ex) {
				log.fatal("Exception Fetching EmployeeDetails Data : " + ex);
			}
		}
		return assocDetails;
	}

	@Override
	public AssociateSalaryDetails getSalaryDetailsForParticularAssocId(final String eId,
			final AssociateSalaryDetails associateSalaryDetails,String queryDate) {
		if (jdbctemp != null) {
			try{
				System.out.println("Fetch Spares Details Query : "+dao_constants.FETCH_ASSOC_SALARY_GEN_DETAILS);
				List<Map<String, Object>> rows = jdbctemp.queryForList(dao_constants.FETCH_ASSOC_SALARY_GEN_DETAILS, new Object[]{eId,queryDate});
				int counter=0;
				List<AssociateDaDetails> listOfAssociateDaDetails = new ArrayList<AssociateDaDetails>();
				String messageText = "success";
				for(Map row : rows){
					
						if(row.get("basesalary")!=null)
						{
							associateSalaryDetails.setBaseSalary((Double)(row.get("basesalary")));
						}
						if(row.get("dailyallowance")!=null)
						{
							associateSalaryDetails.setDailyAllowance((Double)(row.get("dailyallowance")));
						}
						if(row.get("eid")!=null){
							associateSalaryDetails.setEid((String)(row.get("eid")));
						}
						if(row.get("noofleaves")!=null){
							associateSalaryDetails.setLeaveCount((Integer)(row.get("noofleaves")));
						}
						if(row.get("noofdaysworked")!=null)
						{
						associateSalaryDetails.setNoOfDaysWorked((Integer)(row.get("noofdaysworked")));
						}
						if(row.get("totalsalary")!=null){
						associateSalaryDetails.setTotalSalary((Double)(row.get("totalsalary")));
						}

						if(row.get("salarydate")!=null){
							// 05/2016
							Date salDate = (Date)(row.get("salarydate"));
						associateSalaryDetails.setSalaryDate(monthYear.format(salDate));
						}
						 
				}
				associateSalaryDetails.setAssociateDaDetails(getConsolidatedAssociateDADetails(eId,queryDate));
				if(associateSalaryDetails.getTotalSalary()>0){
					messageText = "success";
				}else
				{
					messageText = "No Record Found";
				}
				associateSalaryDetails.setMessage(messageText);
				
			}catch (org.springframework.dao.EmptyResultDataAccessException ex) {
				associateSalaryDetails.setMessage("No Records Found");
			} catch (Exception ex) {
				log.fatal("Exception Fetching EmployeeDetails Data : " + ex);
				ex.printStackTrace();
			}
		}
		return associateSalaryDetails;
	}	
	public List<AssociateDaDetails> getAssociateDADetails(String eId,String fetchDate){
		List<AssociateDaDetails> listOfAssociateDaDetails = new ArrayList<AssociateDaDetails>();
		try{
			List<Map<String, Object>> rows = jdbctemp.queryForList(dao_constants.FETCH_ASSOC_DA_GEN_DETAILS, new Object[]{eId,fetchDate});
			for(Map row : rows){
				if(row.get("datype")!=null){
					AssociateDaDetails daDetails = new  AssociateDaDetails();
					daDetails.setDatype((String)row.get("datype"));
					daDetails.setAmount((Double)row.get("amount"));
					daDetails.setDaperday((Double)row.get("daperday"));
					daDetails.setEid(eId);
					daDetails.setGendate(ft.format((Date)row.get("gendate")));
					daDetails.setNoofdays((Integer)row.get("noofdays")); 
					listOfAssociateDaDetails.add(daDetails);
				}
			} 
		}catch (org.springframework.dao.EmptyResultDataAccessException ex) {
			log.fatal("Exception Fetching EmployeeDetails Data : " + ex);
			ex.printStackTrace();
		} catch (Exception ex) {
			log.fatal("Exception Fetching EmployeeDetails Data : " + ex);
			ex.printStackTrace();
		}
		return listOfAssociateDaDetails;
	}
	@Override	
	public List<AssociateDaDetails> getConsolidatedAssociateDADetails(String eId,String fetchDate){
		List<AssociateDaDetails> actualDADetails = getAssociateDADetails(eId,fetchDate);
		List<AssociateDaDetails> completeAssocDADetailsList = new ArrayList<AssociateDaDetails>();
		List<DaDetails> completeDADetails = getDADetails();
		for(DaDetails daDetails:completeDADetails){
			AssociateDaDetails ascDetails = new AssociateDaDetails();
			ascDetails.setAmount(0);
			ascDetails.setDaperday(daDetails.getAmount());
			ascDetails.setDatype(daDetails.getDaType());
			ascDetails.setNoofdays(0);
			ascDetails.setEid(eId);
			completeAssocDADetailsList.add(ascDetails);
		}
		List<AssociateDaDetails> consolidatedAssocDADetailsList = getConsolidatedDADetails(completeAssocDADetailsList,actualDADetails);
		return consolidatedAssocDADetailsList;
	}
	public List<String> getActualDATypes(List<AssociateDaDetails> actualDADetails){
		List<String> actualDATypes = new ArrayList<String>(); 
		for(AssociateDaDetails actDetail : actualDADetails){
			actualDATypes.add(actDetail.getDatype());
		}
		return actualDATypes;
	}
	public List<AssociateDaDetails> getConsolidatedDADetails(List<AssociateDaDetails> completeDADetails,List<AssociateDaDetails> actualDADetails){
		List<String> actualDATypes = getActualDATypes(actualDADetails);
		if(actualDADetails!=null &&actualDADetails.size()>0){
			for(AssociateDaDetails allDADetail : completeDADetails){
				if(!actualDATypes.contains(allDADetail.getDatype())){
					actualDADetails.add(allDADetail);
				}
			}
		}
		return actualDADetails;
	}

	@Override
	public String addAssocDetails(EmployeeDetails assocDetails) {
		String message = "Failure";
		 
		try {
			System.out.println("Entered add Assoc details !!!!");
			System.out.println("QUERY :" + dao_constants.INSERT_ASSOC);

			int rowCount = jdbctemp.update(
					dao_constants.INSERT_ASSOC,
 					new Object[] {
							assocDetails.getEmployeeId(),assocDetails.getEmployeeName(),assocDetails.getDateOfJoin(),assocDetails.getDesignation(),assocDetails.getDepartment(),assocDetails.getAddress(),assocDetails.getMobileNo()});

			System.out.println("ASSOC added count : "+rowCount); 
			
			message = "success";
		} catch (DataIntegrityViolationException duplicateEntry) {
			message = "DuplicateEntry : " + duplicateEntry.getMessage();
			log.fatal("DuplicateEntry :: Exception while inserting Assoc Data : "
					+ duplicateEntry);

		} catch (Exception exception) {
			// log.fatal("Exception while inserting Customer Data   : "+exception.printStackTrace());
			exception.printStackTrace();
			// System.out.println();
		}
		return message;
	}
	@Override
	public String addDaDetails(DaDetails daDetails) {
		String message = "Failure";
		 
		try {
			System.out.println("Entered add DA details !!!!");
			System.out.println("QUERY :" + dao_constants.INSERT_DA_DETAILS);

			int rowCount = jdbctemp.update(
					dao_constants.INSERT_DA_DETAILS,
 					new Object[] {
							daDetails.getDaType(),daDetails.getAmount(),daDetails.getAsOnDate()});

			System.out.println("DA added count : "+rowCount); 
			
			message = "success";
		} catch (DataIntegrityViolationException duplicateEntry) {
			message = "DuplicateEntry : " + duplicateEntry.getMessage();
			log.fatal("DuplicateEntry :: Exception while inserting DA Data : "
					+ duplicateEntry);

		} catch (Exception exception) {
			// log.fatal("Exception while inserting Customer Data   : "+exception.printStackTrace());
			exception.printStackTrace();
			// System.out.println();
		}
		return message;
	}
	@Override
	public String updateAssocDetails(EmployeeDetails assocDetails) {
		String message = "Failure";
		 
		try {
			System.out.println("Entered Update Assoc details !!!!");
			System.out.println("QUERY :" + dao_constants.UPDATE_ASSOC);

			int rowCount = jdbctemp.update(
					dao_constants.UPDATE_ASSOC,
 					new Object[] {assocDetails.getEmployeeId(),assocDetails.getEmployeeName(),assocDetails.getDateOfJoin(),assocDetails.getDesignation(),assocDetails.getDepartment(),assocDetails.getAddress(),assocDetails.getMobileNo(),assocDetails.getEmployeeId()});

			System.out.println("ASSOC Updated count : "+rowCount); 
			
			message = "success";
		} catch (DataIntegrityViolationException duplicateEntry) {
			message = "DuplicateEntry : " + duplicateEntry.getMessage();
			log.fatal("DuplicateEntry :: Exception while updating Assoc Data : "
					+ duplicateEntry);

		} catch (Exception exception) {
			// log.fatal("Exception while inserting Customer Data   : "+exception.printStackTrace());
			exception.printStackTrace();
			// System.out.println();
		}
		return message;
	}
	@Override
	public String updateAssocSalaryDetails(SalaryDetails assocDetails) {
		String message = "Failure";
		 
		try {
			System.out.println("Entered Update Assoc Salary details !!!!");
			System.out.println("QUERY :" + dao_constants.UPDATE_ASSOC_SALARY_DATA);

			int rowCount = jdbctemp.update(
					dao_constants.UPDATE_ASSOC_SALARY_DATA,
 					new Object[] {assocDetails.getSalary(),assocDetails.geteId(),assocDetails.getAsOnDate()});

			System.out.println("ASSOC Updated count : "+rowCount); 
			
			message = "success";
		} catch (DataIntegrityViolationException duplicateEntry) {
			message = "DuplicateEntry : " + duplicateEntry.getMessage();
			log.fatal("DuplicateEntry :: Exception while updating Assoc Salary Details : "
					+ duplicateEntry);

		} catch (Exception exception) {
			// log.fatal("Exception while inserting Customer Data   : "+exception.printStackTrace());
			exception.printStackTrace();
			// System.out.println();
		}
		return message;
	}	

	@Override
	public FeedbackDetails getFeedbackForParticularId(int feedbackId,
			final FeedbackDetails feedbackDetails) {
		if (jdbctemp != null) {
			try {
				System.out.println("Fetch Feedback Details Query : "
						+ dao_constants.SELECT_FEEDBACK_FOR_SINGLE_ID);

				jdbctemp.queryForObject(
						dao_constants.SELECT_FEEDBACK_FOR_SINGLE_ID,new Object[]{feedbackId},
						new RowMapper<FeedbackDetails>() {

							@Override
							public FeedbackDetails mapRow(ResultSet rs,
									int rowNum) throws SQLException {
								feedbackDetails.setFeedbackId((rs
										.getInt("feedbackid")));
								feedbackDetails.setQualityOfWork((Integer) rs
										.getInt("qualityofwork"));
								feedbackDetails
										.setCleaningOfVehicle((Integer) rs
												.getInt("cleaningofvehicle"));
								feedbackDetails
										.setTimelyCompletion((Integer) rs
												.getInt("timelycompletion"));
								feedbackDetails.setOverallRating((Integer) rs
										.getInt("overallrating"));
								feedbackDetails.setRemark((String) rs
										.getString("remark"));
								feedbackDetails.setJobcardNumber((String) rs
										.getString("jobcardnumber"));
								feedbackDetails.setChasisNumber((String) rs
										.getString("chasisnumber"));
								feedbackDetails.setEid((String) rs
										.getString("eid"));
								feedbackDetails.setIssueStatus((String) rs
										.getString("issuestatus"));
								feedbackDetails.setDateOfFeedback((String) rs
										.getString("dateoffeedback"));
								feedbackDetails.setServicestarttime(rs
										.getString("servicestarttime"));
								feedbackDetails.setServiceendtime(rs
										.getString("serviceendtime"));
								feedbackDetails.setExplanationofproblem(rs
										.getInt("explanationofproblem"));
								feedbackDetails.setExplanationoftractor(rs
										.getInt("explanationoftractor"));
								feedbackDetails.setExplanationofimplement(rs
										.getInt("explanationofimplement"));
								feedbackDetails.setOtherProblemDetails(rs
										.getString("otherproblemdetails"));
								feedbackDetails.setTechnitianreplyforproblem(rs
										.getInt("technicianreplyforproblem"));
								feedbackDetails.setTechnicianSolution(rs
										.getString("techniciansolution"));
								feedbackDetails.setPosttroubleshoottest(rs
										.getInt("posttroubleshoottest"));  
								return feedbackDetails;
							}
						});
				feedbackDetails.setMessage("success");
			} catch (Exception exception) {
				// log.fatal("Exception while inserting Customer Data   : "+exception.printStackTrace());
				exception.printStackTrace();
				// System.out.println();
			}

		}
		return feedbackDetails;
	}
	
	
	public List<ServiceUsageTrendChart> getServiceUsageChartData() {
		List<ServiceUsageTrendChart> datas = new ArrayList<ServiceUsageTrendChart>();
		if (jdbctemp != null) {
		try{
			System.out.println("Fetch Service Six Month Usage Trend Details Query : "
					+ dao_constants.SELECT_LP_LAST_SIX_MONTH_SEVICES_DATA);
			List<Map<String, Object>> rows = jdbctemp.queryForList(dao_constants.SELECT_LP_LAST_SIX_MONTH_SEVICES_DATA);
			
			for (Map row : rows) {
				ServiceUsageTrendChart stc = new ServiceUsageTrendChart(); 
				stc.setMonthyear((String)row.get("monthyear"));
				stc.setNoOfServices((Long)row.get("noOfServices")); 
				datas.add(stc);	
			}
		}catch(Exception ex){
			// log.fatal("Exception while inserting Customer Data   : "+exception.printStackTrace());
			ex.printStackTrace();
			// System.out.println();
		}
		}
		return datas;
	}
	public ValidityCheck getNoOfJobcard(String jobCardNumber) {
		final ValidityCheck count = new ValidityCheck();
		try {
			jdbctemp.queryForObject(dao_constants.JOBCARD_COUNT, new Object[] {jobCardNumber}, new RowMapper<ValidityCheck>(){

				@Override
				public ValidityCheck mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					count.setMessage("SUCCESS");
					count.setPlaceHolderString(rs.getString("chasisNumber"));
					count.setValid(true);
					return count;
 				}
				
			});
		} catch(org.springframework.dao.EmptyResultDataAccessException spEx){
			log.debug("EmptyResultDataAccessException at getNoOfJobcard : " + spEx);
			System.out.println("EmptyResultDataAccessException at getChasisNoList : " + spEx);
			return null;
			
		}catch (Exception e) {
			log.debug("Exception at getNoOfJobcard : " + e);
			System.out.println("Exception at getChasisNoList : " + e);
			return null;
		}
		return count;
	}
	
	public List<Object> getVehicleSaleYears(){
		List<Object> datas = new ArrayList<Object>();
		System.out.println("QUERY : "+dao_constants.SELECT_DISTINCT_YEAR);
		try{
			datas = jdbctemp.queryForList(dao_constants.SELECT_DISTINCT_YEAR, Object.class);
			System.out.println("getVehicleSaleYears : "+datas.toString());
			
		}catch(Exception ex){
			log.debug("Exception at getVehicleSaleYears : ", ex);
			System.out.println("Exception at getVehicleSaleYears : " + ex);
		}
		return datas;
	}
	@Override	
	public List<Object> getSalaryYears(){
		List<Object> datas = new ArrayList<Object>();
		System.out.println("QUERY : "+dao_constants.SELECT_DISTINCT_SALARY_YEAR);
		try{
			datas = jdbctemp.queryForList(dao_constants.SELECT_DISTINCT_SALARY_YEAR, Object.class);
			System.out.println("getSalaryYears : "+datas.toString());
			
		}catch(Exception ex){
			log.debug("Exception at getSalaryYears : ", ex);
			System.out.println("Exception at getSalaryYears : " + ex);
		}
		return datas;
	}	
	
	public List<SparesDiffReport> getSparesDiffReport(String reportType,int year,int limit) {
		List<SparesDiffReport> datas = new ArrayList<SparesDiffReport>();
		if (jdbctemp != null) {
		try{
			if("".equals(reportType)){
			System.out.println("Spares Sold Query : "
					+ dao_constants.SELECT_SPARES_SOLD_VOLUME_YEAR);
			List<Map<String, Object>> rows = jdbctemp.queryForList(dao_constants.SELECT_SPARES_SOLD_VOLUME_YEAR,new Object[]{year,limit});
			
			for (Map row : rows) {
				SparesDiffReport sdr = new SparesDiffReport(); 
				sdr.setNoOfSparesSold((Integer)row.get("volumeSold"));
				sdr.setRevenue((Double)row.get("revenue"));
				sdr.setSparesCode((String)row.get("spare_code"));
				sdr.setSparesDescription((String)row.get("part_description"));
				datas.add(sdr);	
			}
			}else
			{
			/*  PENDING AGENDA */
			}
			
		}catch(Exception ex){
			// log.fatal("Exception while inserting Customer Data   : "+exception.printStackTrace());
			ex.printStackTrace();
			// System.out.println();
		}
		}
		return datas;
	}	

	public int getNoOfPublicHolidays(String startDate, String endDate) {
		int noOfPublicHolidays = 0;
		if (jdbctemp != null) {
			noOfPublicHolidays = jdbctemp.queryForInt(
					dao_constants.SELECT_NO_OF_PUBLIC_HOLIDAY, new Object[] {
							startDate, endDate });
		}
		System.out.println("No Of Public Holidays Between : startDate :"
				+ startDate + " and endDate : " + endDate + " is :"
				+ noOfPublicHolidays);
		return noOfPublicHolidays;

	}
	@Override
	public String insertAssocSalaryDetails(final AssociateSalaryDetails associateSalaryDetails){
		String message="failure";
		TransactionTemplate template = new TransactionTemplate(transactionManager);
		message = (String) template.execute(new TransactionCallback<Object>() {
			  public Object doInTransaction(TransactionStatus transactionStatus) {
				  
				  try{
				  int salGenCounter = jdbctemp.update(dao_constants.INSERT_ASSOC_SALARY_DETAILS,new Object[]{
							associateSalaryDetails.getEid(),associateSalaryDetails.getBaseSalary(),
							associateSalaryDetails.getNoOfDaysWorked(),associateSalaryDetails.getLeaveCount(),
							associateSalaryDetails.getDailyAllowance(),Math.ceil(associateSalaryDetails.getTotalSalary()),
							dateUtil.getFirstDayOfMonth(),dateUtil.getNoOfDaysInCurrentMonth(),associateSalaryDetails.getNoOfWorkingDaysInMonth()
					});
				  //`datype`,`eid`,`gendate`,`noofdays`,`amount`,`daperday`
				  for(AssociateDaDetails daData : associateSalaryDetails.getAssociateDaDetails()){
					  if(daData.getNoofdays()!=0){
					  int daGenCounter = jdbctemp.update(dao_constants.INSERT_ASSOC_DA_DETAILS,new Object[]{
							  daData.getDatype(),associateSalaryDetails.getEid(),dateUtil.getFirstDayOfMonth(),
							  daData.getNoofdays(),daData.getAmount(),daData.getDaperday()
					  });	    
					  }
				  }
				  transactionManager.commit(transactionStatus);
				  return "success";
				  }catch(Exception ex){
					  ex.printStackTrace();
					//  transactionManager.rollback(transactionStatus);
					  transactionStatus.setRollbackOnly();
					  return "failure";
				  } 
				  
			  }
			  }); 
		return message;
	}
	@Override
	public String insertAssocSalaryData(final SalaryDetails associateSalaryDetails){
		String message="failure";
		TransactionTemplate template = new TransactionTemplate(transactionManager);
		message = (String) template.execute(new TransactionCallback<Object>() {
			  public Object doInTransaction(TransactionStatus transactionStatus) {
				  
				  try{
					  // `eid`,`salary`,`asondate`
				  int salGenCounter = jdbctemp.update(dao_constants.INSERT_ASSOC_SALARY_DATA,new Object[]{
							associateSalaryDetails.geteId(),associateSalaryDetails.getSalary(),
							associateSalaryDetails.getAsOnDate()
					});
				  return "success";
				  }catch(Exception ex){
					  ex.printStackTrace();
					//  transactionManager.rollback(transactionStatus);
					  transactionStatus.setRollbackOnly();
					  return "failure";
				  } 
				  
			  }
			  }); 
		return message;
	}	
	@Override
	public SalaryDetails fetchAssocSalaryData(final String eid,final String queryDate){
		SalaryDetails sd=new SalaryDetails();

		try{
			Date date = queryFormat.parse(queryDate);
			sd=jdbctemp.queryForObject(dao_constants.SELECT_SALARY_DATA_FOR_ASSOC,new Object[]{eid,ft.format(date)}, new RowMapper<SalaryDetails>() {

				@Override
				public SalaryDetails mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					SalaryDetails sd= new SalaryDetails();
					sd.seteId(eid);
					sd.setSalary(rs.getDouble("salary"));
					sd.setAsOnDate(ft.format(rs.getDate("asondate")));
					sd.setMessage("success");
					return sd;
				}
			}); 
		}catch(Exception ex){
			  ex.printStackTrace();
		  } 
		return sd;
	}
	@Override
	public String updateAssocSalaryDetails(final AssociateSalaryDetails associateSalaryDetails){
		String message="failure";
		TransactionTemplate template = new TransactionTemplate(transactionManager);
		message = (String) template.execute(new TransactionCallback<Object>() {
			  public Object doInTransaction(TransactionStatus transactionStatus) {		  
				  try{
				  int salGenCounter = jdbctemp.update(dao_constants.UPDATE_ASSOC_SALARY_DETAILS,new Object[]{
							associateSalaryDetails.getEid(),associateSalaryDetails.getBaseSalary(),
							associateSalaryDetails.getNoOfDaysWorked(),associateSalaryDetails.getLeaveCount(),
							associateSalaryDetails.getDailyAllowance(),associateSalaryDetails.getTotalSalary(),
							dateUtil.getFirstDayOfMonth(),dateUtil.getNoOfDaysInCurrentMonth(),associateSalaryDetails.getNoOfWorkingDaysInMonth(),associateSalaryDetails.getNoOfDaysWorked(),associateSalaryDetails.getLeaveCount(),
							associateSalaryDetails.getDailyAllowance(),Math.ceil(associateSalaryDetails.getTotalSalary()),
					});
				  //`noofdays` = ?,`amount` = ?,`daperday` = ?
				  for(AssociateDaDetails daData : associateSalaryDetails.getAssociateDaDetails()){
					  if(daData.getNoofdays()!=0){
					  int daGenCounter = jdbctemp.update(dao_constants.UPDATE_ASSOC_DA_DETAILS,new Object[]{
							  daData.getDatype(),associateSalaryDetails.getEid(),dateUtil.getFirstDayOfMonth(),
							  daData.getNoofdays(),daData.getAmount(),daData.getDaperday(),daData.getNoofdays(),
							  daData.getAmount(),daData.getDaperday()
					  });	    
					  }
				  }
				   
				  return "success";
				  }catch(Exception ex){
					  ex.printStackTrace();
					//  transactionManager.rollback(transactionStatus);
					  transactionStatus.setRollbackOnly();
					  return "failure";
				  } 
				  
			  }
			  }); 
		return message;
	}	
	public String insertEnquiryDetails(EnquiryDetails enquiryDetails){
		String message="failure";
		try{
			System.out.println("INSERT Enquiry Details : "
					+ dao_constants.INSERT_ENQUIRY_DETAILS);
			int count = jdbctemp.update(dao_constants.INSERT_ENQUIRY_DETAILS,new Object[]{ 
						enquiryDetails.getCustomerName(),
						enquiryDetails.getAddress(),
						enquiryDetails.getContactNo(),
						enquiryDetails.getVehicleDetails(),
						enquiryDetails.getReqDetails(),
						enquiryDetails.getEnquiryLocation(),
						enquiryDetails.getEnquiryDate(),
						enquiryDetails.getEnquiryType(),
						enquiryDetails.getCustomerName(),
						enquiryDetails.getAddress(),
						enquiryDetails.getContactNo(),
						enquiryDetails.getVehicleDetails(),
						enquiryDetails.getReqDetails(),
						enquiryDetails.getEnquiryLocation(),
						enquiryDetails.getEnquiryDate(),
						enquiryDetails.getEnquiryType() 
			});
		 if(count>0){
			 return "success";
		 }	else
		 {
			 return "failure";
		 }
		}catch(Exception ex){
			  ex.printStackTrace(); 
			  return "failure";
		  } 
	}	
	@Override
	public String insertOfferSheetDetails(final OfferSheetDetails offerSheetDetails){
		TransactionTemplate template = new TransactionTemplate(transactionManager);
		System.out.println("INSERT OfferSheet Details : "
				+ dao_constants.INSERT_OFFERSHEET_DETAILS);
		System.out.println("INSERT MarginMoney Details : "
				+ dao_constants.INSERT_MM_DETAILS);
				
		try{
			String message = (String) template.execute(new TransactionCallback<Object>() {

				@Override
				public Object doInTransaction(TransactionStatus status) {
					// TODO Auto-generated method stub
					try{
						KeyHolder keyHolder = new GeneratedKeyHolder();
						jdbctemp.update(
							    new PreparedStatementCreator() {
							        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
							            PreparedStatement ps =
							                connection.prepareStatement(dao_constants.INSERT_OFFERSHEET_DETAILS, new String[] {"id"});
							            ps.setString(1, offerSheetDetails.getCustomername());
							            ps.setString(2, offerSheetDetails.getAddress());
							            ps.setString(3, offerSheetDetails.getTaluk());
							            ps.setString(4, offerSheetDetails.getDistrict());
							            ps.setString(5, offerSheetDetails.getCustomerid());
							            ps.setString(6, offerSheetDetails.getContactno());
							            ps.setString(7, offerSheetDetails.getFinancedetails());
							            ps.setDouble(8, offerSheetDetails.getLoanamount());
							            ps.setDouble(9, offerSheetDetails.getMarginamount());
							            ps.setString(10, offerSheetDetails.getOtherdetails());
							            ps.setString(11, offerSheetDetails.getOfferdetails());
							            ps.setString(12, offerSheetDetails.getDate());
							            return ps;
							        }
							    },
							    keyHolder);
						for(MMDetails mmData:offerSheetDetails.getMmDetailsList()){
							jdbctemp.update(dao_constants.INSERT_MM_DETAILS, new Object[]{keyHolder.getKey().intValue(),mmData.getPaymentdate(),mmData.getAmountpaid()});
						}
						transactionManager.commit(status);
						return "success";
					}catch(Exception ex){
						  ex.printStackTrace(); 
						  status.setRollbackOnly();
						  transactionManager.rollback(status);
						  return "failure"; 
					  }  
				}
			});			  
		}catch(Exception ex){
			  ex.printStackTrace();  
			  return "failure"; 
		  }
		return "success"; 
		
	}
	
}
