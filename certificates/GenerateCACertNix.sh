#!/bin/sh
name=CA
certpath=CA
openssl req -x509 -newkey rsa:2048 -keyout $name/$name"privkey.key" -out $name/$name".crt"