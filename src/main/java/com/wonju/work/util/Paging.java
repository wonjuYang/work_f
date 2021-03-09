package com.wonju.work.util;

public class Paging {
	private int nowPage,	//현재 페이지
				rowTotal,	//총 게시물 수
				blockList,	//한 페이지에 표현할 게시물 수
				blockPage,	//한 블럭당 표현할 페이지 수
				totalPage,	//총 페이지 수
				startPage,	//시작 페이지
				endPage,	//끝 페이지
				begin,	//시작 게시물의 행 번호
				end;	//끝 게시물의 행 번호
	
	public int getNowPage() {
		return nowPage;
	}
	public void setNowPage(int nowPage) {
		this.nowPage = nowPage;
	}
	public int getRowTotlal() {
		return rowTotal;
	}
	public void setRowTotlal(int rowTotlal) {
		this.rowTotal = rowTotlal;
	}
	public int getBlockList() {
		return blockList;
	}
	public void setBlockList(int blockList) {
		this.blockList = blockList;
	}
	public int getBlockPage() {
		return blockPage;
	}
	public void setBlockPage(int blockPage) {
		this.blockPage = blockPage;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getStartPage() {
		return startPage;
	}
	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}
	public int getEndPage() {
		return endPage;
	}
	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}
	public int getBegin() {
		return begin;
	}
	public void setBegin(int begin) {
		this.begin = begin;
	}
	public int getEnd() {
		return end;
	}
	public void setEnd(int end) {
		this.end = end;
	}
	public boolean isPrePage() {
		return isPrePage;
	}
	public void setPrePage(boolean isPrePage) {
		this.isPrePage = isPrePage;
	}
	public boolean isNextPage() {
		return isNextPage;
	}
	public void setNextPage(boolean isNextPage) {
		this.isNextPage = isNextPage;
	}
	private boolean isPrePage; //이전으로 갈 수 있나
	private boolean isNextPage; //다음으로 갈 수 있나
	
	//JSP에서 표현할 페이징 HTML코드를 저장할 곳!
	private StringBuffer sb;
	
	
	public StringBuffer getSb() {
		return sb;
	}
	public void setSb(StringBuffer sb) {
		this.sb = sb;
	}
	
	
	//페이징
	public Paging(int nowPage, int rowTotal, int blockList, int blockPage) {
		
		this.nowPage = nowPage;
		this.rowTotal = rowTotal;
		this.blockList = blockList;
		this.blockPage = blockPage;
		
		//이전, 다음 초기화
		isPrePage = false;
		isNextPage = false;
		
		//입력된 전체 게시물의 수를 통해 전체 페이지 수를 구한다
		totalPage = (int)Math.ceil((double)rowTotal/blockList);
		
		//현재 페이지 값이 전체 페이지의 값보다 크다면 전체 페이지 값을 현재 페이지 값으로 지정한다. 
		//왜???
		if(nowPage > totalPage) {
			nowPage = totalPage;
		}
		
		//현재 블럭의 시작페이지 값과 끝 페이지 값을 구한다
		startPage = (nowPage-1)/blockPage*blockPage+1;
		endPage = startPage + blockPage - 1;
		
		//끝페이지 값이 전체 페이지으 값보다 크면 끝페이지의 값을 전체페이지 값으로 지정
		if(endPage > totalPage) {
			endPage = totalPage;
		}
		
		//시작 게시물과 끝 게시물 얻기
		begin = (nowPage-1)*blockList+1;
		end = begin+blockList-1;
		
		
		//이전기능 가능 여부
		if(startPage > 1) {
			isPrePage = true;
		}
		
		
		//다음기능 가능 여부
		if(endPage < totalPage) {
			isNextPage = true;
		}
		
		
		
		
		//html 코드 시작
		sb = new StringBuffer("<ul class='pager'>");
		
		
		//이전 기능 활성화 여부
		if(isPrePage) {
			sb.append("<li class='previous'><a href='/select_data?cPage=");
			sb.append(startPage-blockPage);
			sb.append("'> &lt; </a></li>");
		}else {
			sb.append("<li class='previous disabled'><a href='#'> &lt; </a></li>");
		}
		
		//페이지 보여주기
		for(int i = startPage; i<=endPage; i++) {
			if(i == nowPage) {
				sb.append("<li>");
				sb.append(i);
				sb.append("</li>");
			}else {
				sb.append("<li><a href='/select_data?cPage=");
				sb.append(i);
				sb.append("'>");
				sb.append(i);
				sb.append("</a></li>");
			}
		}
		
		
		//다음 기능
		if(isNextPage) {
			sb.append("<li class='next'><a href='/select_data?cPage=");
			sb.append(startPage+blockPage);
			sb.append("'> &gt; </a></li>");
		}else {
			sb.append("<li class='disabled next'><a href='#'> &gt; </a></li>");
		}
		
		
		sb.append("</ul>");
		

		
	}
	
	
	//페이징
	public Paging(int nowPage, int rowTotal, int blockList, int blockPage, String subject, String keyword) {
		
		this.nowPage = nowPage;
		this.rowTotal = rowTotal;
		this.blockList = blockList;
		this.blockPage = blockPage;
		
		//이전, 다음 초기화
		isPrePage = false;
		isNextPage = false;
		
		//입력된 전체 게시물의 수를 통해 전체 페이지 수를 구한다
		totalPage = (int)Math.ceil((double)rowTotal/blockList);
		
		//현재 페이지 값이 전체 페이지의 값보다 크다면 전체 페이지 값을 현재 페이지 값으로 지정한다. 
		//왜???
		if(nowPage > totalPage) {
			nowPage = totalPage;
		}
		
		//현재 블럭의 시작페이지 값과 끝 페이지 값을 구한다
		startPage = (nowPage-1)/blockPage*blockPage+1;
		endPage = startPage + blockPage - 1;
		
		//끝페이지 값이 전체 페이지으 값보다 크면 끝페이지의 값을 전체페이지 값으로 지정
		if(endPage > totalPage) {
			endPage = totalPage;
		}
		
		//시작 게시물과 끝 게시물 얻기
		begin = (nowPage-1)*blockList+1;
		end = begin+blockList-1;
		
		
		//이전기능 가능 여부
		if(startPage > 1) {
			isPrePage = true;
		}
		
		
		//다음기능 가능 여부
		if(endPage < totalPage) {
			isNextPage = true;
		}
		
		
		
		
		//html 코드 시작
		sb = new StringBuffer("<ul class='pager'>");
		
		
		//이전 기능 활성화 여부
		if(isPrePage) {
			sb.append("<li class='previous'><a href='/search_data?cPage=");
			sb.append(startPage-blockPage);
			
			// subject와 keyword 추가
			sb.append("&subject=");
			sb.append(subject);
			sb.append("&keyword=");
			sb.append(keyword);
			
			sb.append("'> &lt; </a></li>");
		}else {
			sb.append("<li class='disabled previous'><a href='#'>  &lt; </a></li>");
		}
		
		//페이지 보여주기
		for(int i = startPage; i<=endPage; i++) {
			if(i == nowPage) {
				sb.append("<li>");
				sb.append(i);
				sb.append("</li>");
			}else {
				sb.append("<li><a href='/search_data?cPage=");
				sb.append(i);
				
				// subject와 keyword 추가
				sb.append("&subject=");
				sb.append(subject);
				sb.append("&keyword=");
				sb.append(keyword);
				
				sb.append("'>");
				sb.append(i);
				sb.append("</a></li>");
			}
		}
		
		
		//다음 기능
		if(isNextPage) {
			sb.append("<li class='next'><a href='/search_data?cPage=");

			sb.append(startPage+blockPage);
			
			// subject와 keyword 추가
			sb.append("&subject=");
			sb.append(subject);
			sb.append("&keyword=");
			sb.append(keyword);
			sb.append("'> &gt; </a></li>");
		}else {
			sb.append("<li class='disabled next'><a href='#'>  &gt; </a></li>");
		}
		
		
		sb.append("</ul>");
		
		

	}

	
	
	
}
