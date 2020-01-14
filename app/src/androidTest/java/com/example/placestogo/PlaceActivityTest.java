package com.example.placestogo;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;

import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.placestogo.domain.places.Place;
import org.junit.Rule;
import org.junit.Test;
import static androidx.test.espresso.Espresso.onView;

import static androidx.test.espresso.assertion.ViewAssertions.matches;

import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

public class PlaceActivityTest {
    private Place testPlace;

    @Rule
    public ActivityTestRule placeActivityActivityTestRule = new ActivityTestRule<PlaceActivity>(PlaceActivity.class) {
        @Override
        protected Intent getActivityIntent() {
            testPlace = new Place("test", "test", "kwdlakd", 54.00,32.00);

            Context targetContext = InstrumentationRegistry.getInstrumentation()
                    .getTargetContext();
            Intent result = new Intent(targetContext, PlaceActivity.class);
            result.putExtra("place", testPlace);
            return result;
        }
    };

    @Test
    public void main_activity_has_places() {
        onView(withId(R.id.placeName)).check(matches(withText(testPlace.getName())));
    }
}