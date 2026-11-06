@OnlineJobApply
Feature: Online Job apply in Create JobAdvert page 

Scenario: Verify the user able to apply Online Job in School login  

Given User able to login UK Teach in School Recruiter using "SchoolRecruiter" credentials
When User able to validate user in Dashboard page
And User able to fill all the required fields in JobAdvertDetails page in Create Job Advert using "SchoolRecruiter" data
And User able to fill all the required fields in Application_Method page in Create Job for OnlineApplication.
And User able to Select all the required fields in Publishing page in Create Job Advert.
Then Verify the OnlineJob sccessfully created, JobTitle displays in Broadcast page.
