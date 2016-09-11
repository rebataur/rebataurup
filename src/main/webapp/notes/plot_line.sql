
CREATE OR REPLACE FUNCTION plot_line(tbl text,col text, lm int)
  RETURNS bytea
AS $$

import matplotlib
matplotlib.use('Agg') 
import matplotlib.pylab as plt
import io
import numpy as np

#-----------------------------------------------------

sql = 'select {0} from {1} limit {2}'.format(col,tbl,lm)

rv = plpy.execute(sql)

val = [x[col] for x in rv ]

#-----------------------------------------------------


plt.figure()
plt.plot(val)
#plt.title("test")

#--------------------------------------------------
img_buffer = io.BytesIO()
plt.savefig(img_buffer, format = 'png')
img_buffer.seek(0)
return img_buffer.getvalue()



$$ LANGUAGE plpython3u;



select plot_line('salaries','salary',100);


