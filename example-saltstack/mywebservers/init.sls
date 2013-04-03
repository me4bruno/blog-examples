tomcat7:
  pkg:
    - installed
  service:
    - running
    - watch:
      - file: /var/lib/tomcat7/conf/server.xml

/var/lib/tomcat7/conf/server.xml:
  file:
    - managed
    - source: salt://mywebservers/tomcat/server.xml
    - require:
      - pkg: tomcat7

/var/lib/tomcat7/webapps/ROOT/index.html:
  file:
    - managed
    - source: salt://mywebservers/tomcat/index.html
    - require:
      - pkg: tomcat7

apache2:
  pkg:
    - installed
  service:
    - running
    - watch:
      - file: /etc/apache2/sites-available/default

a2enmod proxy proxy_ajp:
  cmd:
    - run
    - require:
      - pkg: apache2

/etc/apache2/sites-available/default:
  file:
    - managed
    - source: salt://mywebservers/apache/sites-available/default
    - require:
      - pkg: apache2