/* -*- Mode: Java; c-basic-offset: 4; tab-width: 20; indent-tabs-mode: nil; -*-
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.tv.firefox.ui.screenshots;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import androidx.test.uiautomator.UiDevice;

import org.junit.After;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.mozilla.tv.firefox.MainActivity;
import org.mozilla.tv.firefox.R;
import org.mozilla.tv.firefox.helpers.MainActivityTestRule;

import tools.fastlane.screengrab.Screengrab;
import tools.fastlane.screengrab.locale.LocaleTestRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;


public class SettingsTest extends ScreenshotTest {

    private UiDevice mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

    @ClassRule
    public static final LocaleTestRule localeTestRule = new LocaleTestRule();

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new MainActivityTestRule();

    @After
    public void tearDown() {
        mActivityTestRule.getActivity().finishAndRemoveTask();
    }

    @Test
    public void showSettingsViews() throws InterruptedException {
        onView(withId(R.id.navUrlInput)).check(matches(isDisplayed()));

        // current settings list view
        onView(withId(R.id.container_web_render)).check(matches(isDisplayed()));

        // This will need to change if the button layout changes. However, such layout
        // changes are infrequent, and updating this will be easy.
        device.pressDPadDown();
        device.pressDPadDown();
        device.pressDPadDown();
        device.pressDPadDown();

        onView(withId(R.id.settings_tile_telemetry)).check(matches(isDisplayed()));

        // capture a screenshot of the default settings list
        Screengrab.screenshot("settings");

        onView(withId(R.id.settings_tile_telemetry)).perform(click());
        takeScreenshotsAfterWait("send-usage-data", 5000);
        mDevice.pressBack();

        onView(withId(R.id.settings_tile_cleardata)).perform(click());
        takeScreenshotsAfterWait("clear-all-data", 5000);
        mDevice.pressBack();
        onView(withId(R.id.settings_tile_about)).perform(click());
        takeScreenshotsAfterWait("about-screen", 5000);
        mDevice.pressBack();
    }
}
