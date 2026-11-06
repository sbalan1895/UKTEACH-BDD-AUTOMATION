@JobSearch
Feature: UK-Teach JobSearch as anonymous user


Scenario:
Validate anonymous user able to perform jobSearch in UK-Teach

Given User navigate to UK-Teach site
When User enters a keyWord in the job searchbox
And User retives the search results
Then User should see jobs relevant to the searched keyword