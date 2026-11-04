Feature: UK-Teach JobDetails page as anonymous user


Scenario:
Validate anonymous user able to view JobDetails in UK-Teach

Given User navigate to UK-Teach site
When User enters a JobName in the job searchbox
And User retives the search results
Then User should see jobs details of the searched job