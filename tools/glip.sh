#!/bin/sh
orig_args=$1
OPTIND=1

payload=""
needs_comma=false;
check_comma ()
{
	if $needs_comma
		then
			payload="$payload,"
	fi
	needs_comma=true;
}
show_help ()
{
	echo "Usage:"
	echo "$0 \"[title]\""
	echo "or"
	echo "$0 -i 'icon_url [optional]' -a 'activity [optional]' -t 'title [optional]' -b 'body [optional]"
}
while getopts "h?i:a:t:b:" opt; do
	case "$opt" in
		h|\?) show_help && exit 0
			;;
		i) check_comma && payload="$payload\"icon\":\"${OPTARG//\"/\\\"}\""
			;;
		a) check_comma && payload="$payload\"activity\":\"${OPTARG//\"/\\\"}\""
			;;
		t) check_comma && payload="$payload\"title\":\"${OPTARG//\"/\\\"}\""
			;;
		b) check_comma && payload="$payload\"body\":\"${OPTARG//\"/\\\"}\""
			;;
	esac
done

shift $((OPTIND-1))

[ "$1" = "--" ] && shift;
if [ -z $payload ]
	then
		payload="\"title\":\"${orig_args//\"/\\\"}\""
fi
curl -H 'Content-Type: application/json' -d "{$payload}" https://hooks.glip.com/webhook/94ab4ac8-191a-4a8c-ad35-5664b75fb5bb
echo $payload
