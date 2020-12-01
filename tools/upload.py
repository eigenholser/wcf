import csv
import datetime
import json
import requests

wcf_api_phones_uri = "http://localhost:8080/wcf/api/phones"
wcf_api_usages_uri = "http://localhost:8080/wcf/api/usages"
date_format_str = '%m-%d-%Y'

def sendRow(uri, row):
    headers = {'Content-Type': 'application/json'}
    resp = requests.post(uri, headers=headers, data=json.dumps(row))
    print(resp)

if __name__ == '__main__':
    print("Phones...")
    with open('../assignment/CellPhone.csv', 'r') as csv_file:
        reader = csv.DictReader(csv_file, skipinitialspace=True)

        for row in reader:
            print(json.dumps(row, indent=4))
            sendRow(wcf_api_phones_uri, row)

    print("Usages...")
    with open('../assignment/CellPhoneUsageByMonth.csv', 'r') as csv_file:
        reader = csv.DictReader(csv_file, skipinitialspace=True)

        for row in reader:
            row["emplyeeId"] = int(row["emplyeeId"])
            row["totalData"] = float(row["totalData"])
            row["totalMinutes"] = int(row["totalMinutes"])
            print(json.dumps(row, indent=4))
            sendRow(wcf_api_usages_uri, row)

