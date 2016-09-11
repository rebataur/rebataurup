
CREATE OR REPLACE FUNCTION plot_line_date(tbl text,col text,dat_col text,title text, lm int)
  RETURNS bytea
AS $$

import matplotlib
matplotlib.use('Agg') 
import matplotlib.pylab as plt
import io
import numpy as np

#-----------------------------------------------------

sql = 'select {0},{1} from {2} limit {3}'.format(col,dat_col,tbl,lm)

rv = plpy.execute(sql)

val = [x[col] for x in rv ]

from datetime import datetime
dates = [datetime.strptime(x[dat_col],"%Y-%m-%d") for x in rv]

#-----------------------------------------------------

fig, ax = plt.subplots()
ax.plot(dates, val, '-')

# rotate and align the tick labels so they look better
fig.autofmt_xdate()

import matplotlib.dates as mdates
ax.fmt_xdata = mdates.DateFormatter('%Y-%m-%d')
plt.title(title)

#--------------------------------------------------
img_buffer = io.BytesIO()
plt.savefig(img_buffer, format = 'png')
img_buffer.seek(0)


return img_buffer.getvalue()

$$ LANGUAGE plpython3u;



select plot_line_date('infy','closeprice','date','stock price over time',10000);


