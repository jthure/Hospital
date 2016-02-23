#!/bin/sh
echo type username
read username
echo type username
read division
echo type type
read type
certpath=User
pass=password
keystore=$username"_keystore"
truststore=$username"_truststore"
keytool -genkeypair -keystore $certpath/$keystore -alias $username -dname "CN=$username;O=$type;OU=$division" -storepass $pass -keypass $pass
keytool -certreq -alias $username -keystore $certpath/$keystore -file $certpath/$username.csr -storepass $pass
openssl x509 -in $certpath/$username.csr -out $certpath/$username.crt -req -CA CA/CA.crt -CAkey CA/CAprivkey.key -CAcreateserial -passin pass:$pass
keytool -importcert -alias ca -keystore $certpath/$keystore -file CA/CA.crt -storepass $pass -noprompt
keytool -importcert -alias $username -keystore $certpath/$keystore -file $certpath/$username.crt -storepass $pass 
#keytool -list -v -keystore $certpath/$keystore -storepass $pass
keytool -import -alias ca -keystore $certpath/$truststore -file CA/CA.crt -storepass $pass -noprompt
rm $certpath/$username.crt $certpath/$username.csr
