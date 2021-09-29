package com.example.app.trabalhofinal_worldwidepublicholiday.View

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.app.trabalhofinal_worldwidepublicholiday.Network.Holiday
import com.example.app.trabalhofinal_worldwidepublicholiday.R
import com.example.app.trabalhofinal_worldwidepublicholiday.databinding.FragmentCalendarBinding
import sun.bob.mcalendarview.listeners.OnDateClickListener
import sun.bob.mcalendarview.vo.DateData
import java.util.*
import kotlin.collections.ArrayList

class CalendarFragment : Fragment() {
    private lateinit var binding: FragmentCalendarBinding;

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_calendar, container, false);
        binding.lifecycleOwner = activity;

        binding.buttonBack.setOnClickListener {
            findNavController().popBackStack();
        }

        val bundle: Bundle? = arguments;
        var holidays: ArrayList<Holiday>? = null;
        var selectedYear: Int? = null;
        if (bundle == null) {
            Toast.makeText(activity, R.string.holidays_not_found, Toast.LENGTH_SHORT).show();
            findNavController().popBackStack()
        } else {
            val args = CalendarFragmentArgs.fromBundle(bundle);
            holidays = ArrayList(args.holidays.toList());
            selectedYear = args.selectedYear;
        }

        binding.calendarViewHolidays.addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
            override fun onViewAttachedToWindow(v: View) {
                configCalendar(holidays, selectedYear);
            }
            override fun onViewDetachedFromWindow(v: View) {
                unsetHolidaysOnCalendar(holidays);
            }
        })

        return binding.root;
    }

    private fun configCalendar(holidays: ArrayList<Holiday>?, selectedYear: Int?) {
        if (holidays.isNullOrEmpty() || selectedYear == null) {
            Toast.makeText(activity, R.string.holidays_not_found, Toast.LENGTH_SHORT).show();
            findNavController().popBackStack();
        } else {
            Toast.makeText(activity, R.string.sucess_load_holidays, Toast.LENGTH_SHORT).show();

            binding.calendarViewHolidays.travelTo(DateData(selectedYear, Calendar.getInstance().get(Calendar.MONTH) + 1, Calendar.getInstance().get(Calendar.DAY_OF_MONTH)));
            setHolidaysOnCalendar(holidays);

            binding.calendarViewHolidays.setOnDateClickListener(object : OnDateClickListener() {
                override fun onDateClick(view: View, date: DateData) {
                    val selectedDate  = date.year.toString() + "-" + date.monthString + "-" + date.dayString;

                    for (holiday in holidays) {
                        if (holiday.date == selectedDate) {
                            binding.textViewHoliday.text = formatHoliday(holiday);
                        }
                    }
                }
            })
        }
    }

    private fun setHolidaysOnCalendar(holidays: ArrayList<Holiday>?) {
        if (holidays != null) {
            for (holiday in holidays) {
                val arrayDate = holiday.date.split("-");
                val year = arrayDate[0].toInt();
                val month = arrayDate[1].toInt();
                val day = arrayDate[2].toInt();

                binding.calendarViewHolidays.markDate(DateData(year, month, day));
            }
        }
    }

    private fun unsetHolidaysOnCalendar(holidays: ArrayList<Holiday>?) {
        if (holidays != null) {
            for (holiday in holidays) {
                val arrayDate = holiday.date.split("-");
                val year = arrayDate[0].toInt();
                val month = arrayDate[1].toInt();
                val day = arrayDate[2].toInt();

                binding.calendarViewHolidays.unMarkDate(DateData(year, month, day));
            }
        }
    }

    private fun formatHoliday(holiday: Holiday): String {
        val arrayDate: List<String> = holiday.date.split("-");
        val formatedDate: String = arrayDate[2] + "/" + arrayDate[1] + "/" + arrayDate[0];

        var holidayType: String = "";
        holiday.types.forEach { type: String ->
            holidayType += when (type) {
                "Public" -> getString(R.string.holiday_type_public);
                "Bank" -> getString(R.string.holiday_type_bank);
                "Authorities" -> getString(R.string.holiday_type_authorities);
                "Optional" -> getString(R.string.holiday_type_optional);
                "Observance" -> getString(R.string.holiday_type_observance);
                else -> getString(R.string.holiday_n_a);
            }
            holidayType += ", "
        }
        holidayType = holidayType.substring(0, holidayType.length - 2);

        return "${getString(R.string.holiday_date)} $formatedDate" +
                "\n${getString(R.string.holiday_local_name)} ${holiday.localName}" +
                "\n${getString(R.string.holiday_international_name)} ${holiday.name}" +
                "\n${getString(R.string.holiday_country_code)} ${holiday.countryCode}" +
                "\n${getString(R.string.holiday_every_day_all_year)} ${if (holiday.fixed) getString(R.string.holiday_yes) else getString(R.string.holiday_no)}" +
                "\n${getString(R.string.holiday_all_country)} ${if (holiday.global) getString(R.string.holiday_yes) else getString(R.string.holiday_no)}" +
                "\n${getString(R.string.holiday_counties)} ${if (holiday?.counties.isNullOrBlank()) getString(R.string.holiday_n_a) else holiday?.counties}" +
                "\n${getString(R.string.holiday_launch_year)} ${holiday?.launchYear ?: getString(R.string.holiday_n_a)}" +
                "\n${getString(R.string.holiday_types)} $holidayType";
    }
}