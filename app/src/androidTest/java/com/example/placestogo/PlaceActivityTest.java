package com.example.placestogo;

import android.content.Context;
import android.content.Intent;

import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.placestogo.domain.places.Place;
import org.junit.Rule;
import org.junit.Test;
import static androidx.test.espresso.Espresso.onView;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;

import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.Intents.intending;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertTrue;

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
    public IntentsTestRule compassActivityRule = new IntentsTestRule<>(CompassActivity.class);

    @Test
    public void place_activity_has_place() {
        onView(withId(R.id.placeName)).check(matches(withText(testPlace.getName())));
    }

    @Test
    public void place_activity_redirects_to_compass_activity() {
        onView(withId(R.id.buttonCompass)).perform(click()).check(matches(isDisplayed()));
        onView(withId(R.id.tvDestination)).check(matches(withText(testPlace.getName()))).check(matches(isDisplayed()));

        intended(hasComponent(CompassActivity.class.getName()));
    }
}