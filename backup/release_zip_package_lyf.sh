git config --global http.postBuffer 1048576000

WORKSPACE=/Users/mac/Desktop/QuickStart/app
jgPath=/Users/mac/Desktop/tool/360jiagu/jiagu
keystorePath=/Users/mac/Desktop/Android_lyf_ody/lyf_app/keystore
wallePath=/Users/mac/Desktop/tool/walle/walle-cli
buildtools25=/Users/mac/Library/Android/sdk/build-tools/25.0.2
backup_Dir=/Users/mac/Desktop/Version/takeoutBusinessbackup
BuildLog=/Users/mac/Desktop/Version

cd ${WORKSPACE}
cd ..

echo 开始打包
sh ./gradlew clean assembleRelease | grep -v 'takeoutBusinessReleasebuildlog.txt' > ${BuildLog}/takeoutBusinessReleasebuildlog.txt
while read channelsline
do
if echo "$channelsline"|grep -q -E "^:"
then
continue
elif [ "$channelsline" = "BUILD SUCCESSFUL" ];then
echo 'BUILD SUCCESSFUL'
break
elif [ "$channelsline" = "BUILD FAILED" ];then
echo 'BUILDFAILED'
cat ${BuildLog}/takeoutBusinessReleasebuildlog.txt
exit
fi
done <${BuildLog}/takeoutBusinessReleasebuildlog.txt



java -jar ${jgPath}/jiagu.jar -login wuyishun_kmk@outlook.com nihao@123456
java -jar ${jgPath}/jiagu.jar -importsign ${keystorePath}/laiyifen.key laiyifen laiyifen  laiyifen
java -jar ${jgPath}/jiagu.jar -showsign
java -jar ${jgPath}/jiagu.jar -showconfig
java -jar ${jgPath}/jiagu.jar  -showmulpkg


rm -rf ${WORKSPACE}/build/outputs/jiagu
mkdir ${WORKSPACE}/build/outputs/jiagu
#加固
echo 开始加固
ls ${WORKSPACE}/build/outputs/apk/release/ |grep -v 'apkjiagulist.txt' > ${WORKSPACE}/build/apkjiagulist.txt
while read lineapk
do
echo ${WORKSPACE}/build/outputs/apk/${lineapk}
java -jar ${jgPath}/jiagu.jar -jiagu ${WORKSPACE}/build/outputs/apk/release/${lineapk} ${WORKSPACE}/build/outputs/jiagu/ -autosign
done < ${WORKSPACE}/build/apkjiagulist.txt
echo 加固结束

duiqiisOk=false
rm -rf ${WORKSPACE}/build/outputs/duiqi
mkdir ${WORKSPACE}/build/outputs/duiqi
#对齐
echo zipalign4kstart
ls ${WORKSPACE}/build/outputs/jiagu/ |grep -v 'apkduiqilist.txt' > ${WORKSPACE}/build/apkduiqilist.txt
while read lineduiqi
do
duiqiisOk=ok
echo ${WORKSPACE}/build/outputs/jiagu/${lineduiqi}
${buildtools25}/zipalign -v 4   ${WORKSPACE}/build/outputs/jiagu/${lineduiqi}  ${WORKSPACE}/build/outputs/duiqi/${lineduiqi}
done < ${WORKSPACE}/build/apkduiqilist.txt
if [ "$duiqiisOk" != ok ];then
echo zipalignfailed
exit
echo zipalign4kok
fi


#签名
echo startsign
ls ${WORKSPACE}/build/outputs/duiqi/ |grep -v 'apksign.txt' > ${WORKSPACE}/build/apksign.txt
while read linesign
do
echo ${WORKSPACE}/build/outputs/duiqi/${linesign}
${buildtools25}/apksigner sign --ks   ${keystorePath}/laiyifen.key --ks-key-alias laiyifen --ks-pass pass:laiyifen  ${WORKSPACE}/build/outputs/duiqi/${linesign}
done < ${WORKSPACE}/build/apksign.txt
echo signsuccess


apkName
uploadPath
rm -rf ${WORKSPACE}/build/outputs/channels
mkdir ${WORKSPACE}/build/outputs/channels
#多渠道
ls ${WORKSPACE}/build/outputs/duiqi/ |grep -v 'apkchannels.txt' > ${WORKSPACE}/build/apkchannels.txt
while read linechannel
do
echo ${linechannel}
apkName=${linechannel}
done <${WORKSPACE}/build/apkchannels.txt
echo path:${WORKSPACE}/build/outputs/duiqi/${apkName}

while read channelsline
do
if echo "$channelsline"|grep -q -E "^$|^#"
then
continue
fi
substr=${channelsline%#*}
echo 渠道${substr}start
substr=$(echo $substr)
echo ${WORKSPACE}/build/outputs/channels/${substr}_${apkName}
uploadPath=${WORKSPACE}/build/outputs/channels/${substr}_${apkName}
java -jar ${wallePath}/walle-cli-all.jar put -c ${substr} ${WORKSPACE}/build/outputs/duiqi/${apkName}   ${uploadPath}
echo 渠道${substr}end
done <${WORKSPACE}/channel

#上传
curl -F "file=@"${uploadPath} -F "uKey=ed639f2e5cac76e08c1eb24b775c2b69" -F "_api_key=30ddd93225add3ed02677a8ae9722d80" https://qiniu-storage.pgyer.com/apiv1/app/upload

#备份
rm -rf ${backup_Dir}/
mkdir ${backup_Dir}/
cp -R ${WORKSPACE}/build/* ${backup_Dir}/