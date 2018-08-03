package org.ruanwei.demo.springframework.web.conversion;

import java.beans.PropertyEditorSupport;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ruanwei.demo.util.Counter;
import org.ruanwei.demo.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.support.WebBindingInitializer;

@ControllerAdvice
public class MyWebBindingInitializer implements WebBindingInitializer {
	private static final Logger logger = LogManager.getLogger();

	@Override
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		logger.debug("initBinder==================" + Counter.getCount());
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
		SimpleDateFormat datetimeFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		dateFormat.setLenient(false);
		timeFormat.setLenient(false);
		datetimeFormat.setLenient(false);

		binder.registerCustomEditor(int.class, new IntegerEditor());
		binder.registerCustomEditor(java.sql.Date.class, new DateEditor(
				dateFormat));// mysql DATE type
		binder.registerCustomEditor(java.sql.Time.class, new TimeEditor(timeFormat));// mysql TIME type
		binder.registerCustomEditor(java.util.Date.class, new DateTimeEditor(
				datetimeFormat));// mysql DATETIME
		binder.registerCustomEditor(java.sql.Timestamp.class, new TimestampEditor(
				datetimeFormat));// mysql TIMESTAMP type

	}

	class IntegerEditor extends PropertyEditorSupport {
		@Override
		public void setAsText(String text) throws IllegalArgumentException {
			logger.debug("IntegerEditor======================" + text);
			if (text == null || text.equals(""))
				text = "0";
			if (!StringUtils.isDigit(text)) {
				setValue(-1);
			} else {
				setValue(Integer.parseInt(text));
			}
		}

		@Override
		public String getAsText() {
			String r = getValue().toString();
			return "0".equals(r) ? "" : r;
		}
	}

	class DateEditor extends PropertyEditorSupport {
		private SimpleDateFormat format;

		public DateEditor(SimpleDateFormat format) {
			this.format = format;
		}

		@Override
		public void setAsText(String text) throws IllegalArgumentException {
			logger.debug("DateEditor======================" + text);
			if (text == null || text.isEmpty())
				setValue(null);
			else {
				try {
					setValue(new java.sql.Date(this.format.parse(text)
							.getTime()));
				} catch (ParseException ex) {
					throw new IllegalArgumentException("Could not parse date: "
							+ ex.getMessage(), ex);
				}
			}
		}

		@Override
		public String getAsText() {
			java.sql.Date value = (java.sql.Date) getValue();
			return (value != null ? this.format.format(value) : "");
		}
	}

	class DateTimeEditor extends PropertyEditorSupport {
		private SimpleDateFormat format;

		public DateTimeEditor(SimpleDateFormat format) {
			this.format = format;
		}

		@Override
		public void setAsText(String text) throws IllegalArgumentException {
			logger.debug("DateTimeEditor======================" + text);
			if (text == null || text.equals(""))
				setValue(null);
			else {
				try {
					setValue(this.format.parse(text));
				} catch (ParseException ex) {
					throw new IllegalArgumentException("Could not parse date: "
							+ ex.getMessage(), ex);
				}
			}
		}

		@Override
		public String getAsText() {
			Date value = (Date) getValue();
			return (value != null ? this.format.format(value) : "");
		}
	}

	class TimeEditor extends PropertyEditorSupport {
		private SimpleDateFormat format;

		public TimeEditor(SimpleDateFormat format) {
			this.format = format;
		}

		@Override
		public void setAsText(String text) throws IllegalArgumentException {
			logger.debug("TimeEditor======================" + text);
			if (text == null || text.equals(""))
				setValue(null);
			else {
				try {
					setValue(new java.sql.Time(this.format.parse(text)
							.getTime()));
				} catch (ParseException ex) {
					throw new IllegalArgumentException("Could not parse date: "
							+ ex.getMessage(), ex);
				}
			}
		}

		@Override
		public String getAsText() {
			Time value = (Time) getValue();
			return (value != null ? this.format.format(value) : "");
		}
	}

	class TimestampEditor extends PropertyEditorSupport {
		private SimpleDateFormat format;

		public TimestampEditor(SimpleDateFormat format) {
			this.format = format;
		}

		@Override
		public void setAsText(String text) throws IllegalArgumentException {
			logger.debug("TimestampEditor======================" + text);
			if (text == null || text.equals(""))
				setValue(null);
			else {
				try {
					setValue(new java.sql.Timestamp(this.format.parse(text)
							.getTime()));
				} catch (ParseException ex) {
					throw new IllegalArgumentException("Could not parse date: "
							+ ex.getMessage(), ex);
				}
			}
		}

		@Override
		public String getAsText() {
			Timestamp value = (Timestamp) getValue();
			return (value != null ? this.format.format(value) : "");
		}
	}

}
