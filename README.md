# WCF Coding Exercise

## Instructions

A few steps will get the application running:

    mvn wildfly:start

And then:

    mvn wildfly:deploy
    cd tools
    python upload.py

## Implementation Notes

Implementated using JEE8 (or is it EE4J now?) with Wildfly 18.

There are no unit or integration tests. Shocking for me but it was a holiday
last week. :)

Very simple report with three months of data hard-coded from the latest date
usage data is available. It is a bit contrived. A more reasonable report might
take the start and end date as a querystring parameter with certain limits
applied. The layout of the report might need some additional flexibility to
accomodate greater or lesser ranges of data.

The `month_year` header calculation is wacky and should not be implemented
outside the laboratory.

The usage CSV file has a typo in one of the headers. Maybe that was part of the
exercise? I left it in there and translated it with an annotation in the entity
object.

