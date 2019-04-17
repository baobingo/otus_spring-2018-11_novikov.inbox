#Используем powershell из под админа
#Скачиваем https://github-production-release-asset-2e65be.s3.amazonaws.com/56353740/a153fb80-5067-11e9-91f9-02f73dcdbffd?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAIWNJYAX4CSVEH53A%2F20190416%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20190416T125820Z&X-Amz-Expires=300&X-Amz-Signature=41edade3268e9fcbe7d815100c7da1ae1ef5c32b9668fa3ab41a9fd24fcd3339&X-Amz-SignedHeaders=host&actor_id=18487246&response-content-disposition=attachment%3B%20filename%3Dminikube-windows-amd64.exe&response-content-type=application%2Foctet-stream
#Cкачиваем https://storage.googleapis.com/kubernetes-release/release/v1.14.0/bin/windows/amd64/kubectl.exe
#Используем Hyper-V вместо Virtualbox, иначе придется отключать Hyper-v и докер на локальной машине не будет работать, в minikube свой докер
#В Hyper-v предварительно сделаем виртуальную сеть "ext_network"
.\minikube.exe start --vm-driver hyperv --hyperv-virtual-switch "ext_network"
.\minikube stop
#Надо отключить у виртуальной машины использование динамической RAM в Hyper-v
.\minikube start
#Переключаем контекст докера на кубер
.\minikube.exe docker-env | Invoke-Expression
#Билдим имейдж в репо кубера
Docker build -t spring17:latest .
#Деплоем конфиг
.\kubectl.exe apply -f .\two-container-pod.yaml
#Создаем сервис для доступа из вне
.\kubectl expose deployment two-containers --type="NodePort"
#Получаем доступ к машинам и убеждаемся что всё работает
.\minikube.exe service two-containers --url