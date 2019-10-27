package com.gamorales.testapplication.core.controllers

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gamorales.testapplication.R
import com.gamorales.testapplication.core.models.Fixture
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.time.*


class RecyclerAdapter (var fixtures: List<Fixture>, var context: Context) :
    RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = fixtures.get(position)
        holder.bind(item, context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(
            layoutInflater.inflate(R.layout.fragments_list, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return fixtures.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val fixtureCompetition = view.findViewById(R.id.tvFixturesCompetition) as TextView
        val fixturePostponed = view.findViewById(R.id.tvFixturesPostponed) as RelativeLayout
        val fixtureVenueName = view.findViewById(R.id.tvFixturesVenueName) as TextView
        val fixtureDate = view.findViewById(R.id.tvFixturesDate) as TextView
        val fixtureHomeTeam = view.findViewById(R.id.tvFixturesHomeTeam) as TextView
        val fixtureAwayTeam = view.findViewById(R.id.tvFixturesAwayTeam) as TextView
        val fixtureDayMonthScoreHome = view.findViewById(R.id.tvFixturesDayMonthScoreHome) as TextView
        val fixtureDayWeekScoreAway = view.findViewById(R.id.tvFixturesDayWeekScoreAway) as TextView

        fun bind(fixture: Fixture, context: Context){

            // Change date to user timezone
            val formatter = DateTimeFormatter.ISO_DATE_TIME
            val zonedDateTime = ZonedDateTime.parse(fixture.date, formatter)
            fixtureDate.text = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).format(zonedDateTime)

            fixtureCompetition.text = fixture.competitionStage!!.competition!!.name
            fixtureVenueName.text = fixture.venue!!.name + " | "
            fixtureHomeTeam.text = fixture.homeTeam!!.name
            fixtureAwayTeam.text = fixture.awayTeam!!.name

            // When state is postponed, then shows and change date color
            if (fixture.state.equals("postponed")) {
                fixturePostponed.visibility = View.VISIBLE
                fixtureDate.setTextColor(Color.parseColor("#FF0000"))
            } else {
                fixturePostponed.visibility = View.INVISIBLE
                fixtureDate.setTextColor(Color.parseColor("#961A1818"))
            }

            // If there is a score in the object, then change the views values
            if (fixture.score!=null) {
                var home = fixture.score!!.home
                var away = fixture.score!!.away

                // The winner highlight the score
                if (home>away) {
                    fixtureDayMonthScoreHome.setTextColor(Color.parseColor("#1F91F3"))
                    fixtureDayWeekScoreAway.setTextColor(Color.parseColor("#A1A1A3"))
                } else if (away>home) {
                    fixtureDayMonthScoreHome.setTextColor(Color.parseColor("#A1A1A3"))
                    fixtureDayWeekScoreAway.setTextColor(Color.parseColor("#1F91F3"))
                } else {
                    fixtureDayMonthScoreHome.setTextColor(Color.parseColor("#A1A1A3"))
                    fixtureDayWeekScoreAway.setTextColor(Color.parseColor("#A1A1A3"))
                }
                fixtureDayMonthScoreHome.text = home.toString()
                fixtureDayWeekScoreAway.text = away.toString()

                fixtureDayWeekScoreAway.textSize = 24f


            } else {
                fixtureDayMonthScoreHome.text = zonedDateTime.dayOfMonth.toString()
                fixtureDayWeekScoreAway.text = zonedDateTime.dayOfWeek.toString().substring(0..2)
                fixtureDayWeekScoreAway.textSize = 13f
            }
        }
    }
}