### ScannPal - Your Online Detective!

ScannPal was created as our University Senior Design Project from Fall of 2023 to Spring of 2024.  Within this project, we used the Java Selenium Library to scan a specified website using multiple virtual machines (EC2 instances) concurrently.  Within each virtual machine, we tested for **Cross Site Scripting (XSS)**, **SQL Injections (SQLi)**, and **Command Injection attacks**.

In order to test this application, we hosted a local Docker instance (DVWA) running the attacks on their respective pages.

DVWA: `https://hub.docker.com/r/vulnerables/web-dvwa`
