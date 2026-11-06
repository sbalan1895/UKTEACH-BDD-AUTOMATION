@MicroSites_Navigation
Feature: Validate navigation across all Microsite pages 


Scenario:
Verify navigation to CountyLocations, Blogs, and About Us pages

Given the user navigates to the UK-Teach website

When the user selects a county from the Location section
Then the respective County Microsite page should open successfully

When the user navigates to the Blogs page
Then the Blogs page should be displayed successfully

When the user navigates to the About Us page
Then the About Us page should be displayed successfully

And the user should be able to navigate successfully across all Microsite pages