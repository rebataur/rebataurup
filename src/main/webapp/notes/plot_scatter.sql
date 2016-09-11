
CREATE OR REPLACE FUNCTION plot_scatter (tbl text,col text, lm int)
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

sal = [x[col] for x in rv ]

#-----------------------------------------------------

N = 50
x = sal
y = sal
colors = np.random.rand(N)
area = np.pi * (15 )**2  # 0 to 15 point radiuses

plt.scatter(x, y, s=area, c=colors, alpha=0.5)


#--------------------------------------------------
img_buffer = io.BytesIO()
plt.savefig(img_buffer, format = 'png')
img_buffer.seek(0)
return img_buffer.getvalue()



$$ LANGUAGE plpython3u;



select plot_scatter('salaries','salary',100);


