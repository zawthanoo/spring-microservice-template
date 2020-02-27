###Set Up Prometheus

1. Download prometheus - `https://prometheus.io/download/``
 
2. create `spring-prometheus-app.yml`

```yml
#Global configurations
global:
  scrape_interval:     5s # Set the scrape interval to every 5 seconds.
  evaluation_interval: 5s # Evaluate rules every 5 seconds.
scrape_configs:
  - job_name: 'spring-boot-client-1'
    metrics_path: '/actuator/prometheus'
    static_configs:
      - targets: ['localhost:8080']
```
3. Run `prometheus.exe --config.file=YAML_FILE_PATH --web.listen-address=:9999`

   Eg: `prometheus.exe --config.file=spring-prometheus-app.yml --web.listen-address=:9999` 	 

4. Check `http://localhost:9999/`   

###Set Up Grafana 

1. Download Grafana - `https://grafana.com/grafana/download``

2. Execute `grafana-server.exe`\

3. Check `http://localhost:3000/`   

4. default `username` and `password` is `admin` and `admin` (change password after fist time login)


###Add Prometheus as a Data Source in Grafana

	1. Click on "Add Data Source." This will open a page to add a data source.
	2. Give a suitable name to this new data source, as this will be used while creating visualizations. I am using "prometheus-local."
	3. Select Prometheus in the "type" drop down.
	4. The URL shall be "http://localhost:9999" as we have Prometheus running on local host on port 9090.
	5. Fill other details if you have any security or HTTP related settings.
	6. Click "Save & Test."
	7. If Grafana is able to make connections to Prometheus instance with the details provided, then you will get a message saying "Data source is working." If you get any errors, review your values.


