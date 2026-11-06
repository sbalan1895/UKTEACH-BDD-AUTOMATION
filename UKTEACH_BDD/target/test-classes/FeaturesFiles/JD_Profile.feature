@ProfileAndViewAllJobs
Feature: Profile and View All Jobs Navigation on the Job Details Page 


Scenario:
Profile and View All Jobs Navigation on the Job Details Page


Given User navigate to UK-Teach site
When User enters a JobName in the job searchbox
And Validate user into JobDetails page

When the user clicks on the "Profile" link
Then the user should be navigated to the respective School Profile page

When the user navigates back to the Job Details page
And clicks on the "View All Jobs" link
Then the user should be navigated to the respective school's All Jobs page
And the user should be able to view all jobs posted by that school