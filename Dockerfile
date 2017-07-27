from ubuntu:14.04

# Install prerequisites
run apt-get install -y git ncdu htop wget mc unzip &&

# Copy all data
copy . /dir/to/twitter-stats

# Setup the build directory

run cd && mkdir tests
workdir ~/tests
wget -c https://raw.githubusercontent.com/softctrl/twitter-stats/master/projects/shell/setup_env.sh && chmod +x setup_env.sh && ./setup_env.sh


entrypoint ["twitter-stats"]
