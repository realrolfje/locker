#!/usr/bin/env bash

commonname=locker
filename=default
password=defaultpassword
outputdirectory=../../../target
resourcedirectory=../resources

echo
echo "Removing existing keys, certs and stores from ${outputdirectory}"
rm -f ${outputdirectory}/${filename}.key
rm -f ${outputdirectory}/${filename}.cert
rm -f ${outputdirectory}/${filename}.p12
rm -f ${outputdirectory}/${filename}.jks

echo
echo "Creating private key"
openssl genrsa -out ${outputdirectory}/${filename}.key 4096

echo "Creating self signed certificate belonging to the private key"
openssl req -new -x509 \
        -key ${outputdirectory}/${filename}.key \
        -out ${outputdirectory}/${filename}.cert \
        -days 3650 \
        -subj /CN=${commonname}

echo "Creating PKCS12 keystore from private key and public certificate."
openssl pkcs12 -export \
        -name ${commonname} \
        -in ${outputdirectory}/${filename}.cert \
        -inkey ${outputdirectory}/${filename}.key \
        -out ${outputdirectory}/${filename}.p12 \
        -password pass:${password}

echo "Converting PKCS12 keystore into a JKS keystore"
keytool -importkeystore \
        -srckeystore ${outputdirectory}/${filename}.p12 \
        -srcstoretype pkcs12 \
        -srcstorepass ${password} \
        -alias ${commonname} \
        -destkeystore ${outputdirectory}/${filename}.jks \
        -deststorepass ${password} \
        -noprompt

echo
echo "Generated files:"
ls -al ${outputdirectory}/${filename}.*

echo "Copied ${filename} to ${resourcedirectory}."
cp ${outputdirectory}/${filename}.jks ${resourcedirectory}

echo
echo "${filename}.jks contains the key/cert combination for our https server."
echo "The password for the keystore is '${password}'."