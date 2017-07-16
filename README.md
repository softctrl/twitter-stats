# twitter-stats
Just a challenge.

To use this just:

```
mkdir tests && cd tests && \
wget -c https://raw.githubusercontent.com/softctrl/twitter-stats/master/projects/shell/setup_env.sh && chmod +x setup_env.sh && \
./setup_env.sh
```

Also you can use the [insomnia](https://insomnia.rest/) with the provided [Insomnia_2017-07-15.json](https://raw.githubusercontent.com/softctrl/twitter-stats/master/Insomnia_2017-07-15.json) to make some tests.

You need to use a machine:

1) 64Bits
2) With wget, nohup, unzip and all utils that a regular linux distribuition need to have.
3) Access to a valid internet connection.

So, the service will be running on the 8070 port and you can make tests on console:
```
curl --request GET \
  --url 'http://localhost:8070/movies/best?size=2'
  
curl --request GET \
  --url http://localhost:8070/genres/best

curl --request GET \
  --url http://localhost:8070/genres/countmovies

curl --request GET \
  --url 'http://localhost:8070/genres/ratingavgbygenre?id=20'

curl --request GET \
  --url http://localhost:8070/movies/ratingavgyear

curl --request GET \
  --url http://localhost:8070/movies/countbyyear

curl --request GET \
  --url http://localhost:8070/movies/countbydecade
```

Also i put a vagrant [machine](https://github.com/softctrl/twitter-stats/tree/master/ubuntu-vagrant) that you can use to test this project.

Under development.
